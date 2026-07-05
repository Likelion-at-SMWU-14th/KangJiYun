from django.urls import path, include
from rest_framework import routers
from .views import UserModelViewSet

app_name = "accounts"

router_accounts = routers.DefaultRouter()
router_accounts.register("", UserModelViewSet)

urlpatterns = [
    path("", include(router_accounts.urls)),
]
