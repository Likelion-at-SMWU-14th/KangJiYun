from django.urls import path
from . import views 

urlpatterns = [
    path('signup/', views.signup, name='signup'),
    path('<int:user_id>/', views.user_detail, name='user_detail')
]
