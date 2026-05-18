from django.db import models
from django.contrib.auth import get_user_model

# Create your models here.
User = get_user_model()

class Post(models.Model):
    title = models.CharField(max_length=200,verbose_name='제목')
    content = models.TextField(verbose_name='내용')
    writer = models.ForeignKey(User,on_delete=models.CASCADE,verbose_name='작성자',null=True, blank=True)
    created_at = models.DateTimeField(verbose_name='작성일', auto_now_add=True)