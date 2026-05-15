from django.shortcuts import render
from .models import Post
from django.views import View


# Create your views here.
def post_list(request):
    posts = Post.objects.all()
    context = {"view_type": "Function Based View", "posts": posts}
    return render(request, "post_list.html", context)

class post_detail(View):
    def get(self, request, id):
        post = Post.objects.get(id=id)
        context={"view_type": "Class Based View", "post":post}
        return render(request, "post_detail.html", context)