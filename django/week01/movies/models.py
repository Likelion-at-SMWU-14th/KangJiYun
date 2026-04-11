from django.db import models

# Create your models here.


class Movie(models.Model):
    title = models.CharField(verbose_name="제목", max_length=200)
    post = models.ImageField(verbose_name="포스터", blank=True, null=True)
    director = models.CharField(verbose_name="감독", max_length=100)
    genre = models.CharField(verbose_name="장르", max_length=100)
    content = models.TextField(verbose_name="내용", blank=True, null=True)
    release_date = models.DateField(verbose_name="개봉일")
    is_seen = models.BooleanField(verbose_name="관람여부", default=False)


class Comment(models.Model):
    movie = models.ForeignKey(Movie, verbose_name="영화", on_delete=models.CASCADE)
    rating = models.IntegerField(verbose_name="평점", default=10)
    content = models.TextField(verbose_name="감상평")
    created_at = models.DateTimeField(verbose_name="작성일", auto_now_add=True)
