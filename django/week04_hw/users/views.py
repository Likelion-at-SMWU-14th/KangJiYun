from django.shortcuts import render
from rest_framework.decorators import api_view
from rest_framework.response import Response
from .serializers import UserSerializer
from django.contrib.auth.models import User

@api_view(['POST'])
def signup(request):
    serializer=UserSerializer(data=request.data)
    if serializer.is_valid():
        serializer.save()
        return Response(serializer.data, status=201)
    return Response(serializer.errors, status=400) 

@api_view(['GET','PATCH','DELETE'])
def user_detail(request, user_id):
    try:
        user=User.objects.get(pk=user_id)
    except User.DoesNotExist:
        return Response({'error:':'존재하지 않는 유저입니다.'}, status=404)
    
    if request.method=='GET':
        return Response(UserSerializer(user).data)
    if request.method=='PATCH':
        serializer=UserSerializer(user,data=request.data, partial=True)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=200)
        return Response(serializer.errors, status=400)