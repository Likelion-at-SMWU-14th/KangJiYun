from rest_framework.viewsets import ModelViewSet
from rest_framework.permissions import IsAuthenticatedOrReadOnly
from .models import Post
from .serializers import (
    PostListSerializer,
    PostRetrieveSerializer,
    PostCreateSerializer,
)


class PostModelViewSet(ModelViewSet):
    queryset = Post.objects.all()
    permission_classes = [IsAuthenticatedOrReadOnly]

    def get_serializer_class(self):
        if self.action == "list":
            return PostListSerializer
        elif self.action == "retrieve":
            return PostRetrieveSerializer
        elif self.action in ["create", "update", "partial_update"]:
            return PostCreateSerializer
        return PostListSerializer

    def perform_create(self, serializer):
        serializer.save(writer=self.request.user)