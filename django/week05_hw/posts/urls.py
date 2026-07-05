from django.urls import path, include
from rest_framework import routers
from .views import PostModelViewSet

app_name = "posts"

router_post = routers.DefaultRouter()
router_post.register("", PostModelViewSet)

urlpatterns = [
    path("", include(router_post.urls)),
]