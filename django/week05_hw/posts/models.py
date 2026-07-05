from django.db import models
from django.contrib.auth import get_user_model

User = get_user_model()

class Post(models.Model):
    writer = models.ForeignKey(
        User,
        on_delete=models.CASCADE,
        verbose_name="작성자",
        null=True,
        blank=True,
    )
    title = models.CharField(max_length=100, verbose_name="제목")
    content = models.TextField(verbose_name="내용")
    created_at = models.DateTimeField(verbose_name="작성일", auto_now_add=True)
    view_count = models.PositiveIntegerField(verbose_name="조회수", default=0)
