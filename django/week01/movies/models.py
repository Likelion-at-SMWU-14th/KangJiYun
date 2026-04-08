from django.db import models

# Create your models here.


class Movie(models.Model):
    title = models.CharField(verbose_name="제목")
    post = models.ImageField(verbose_name="포스터", blank=True, null=True)
    director = models.CharField(verbose_name="감독")
    genre = models.CharField(verbose_name="장르")
    content = models.TextField(verbose_name="내용", blank=True, null=True)
    release_date = models.DateField(verbose_name="개봉일")
    is_seen = models.BooleanField(verbose_name="관람여부", default=False)
