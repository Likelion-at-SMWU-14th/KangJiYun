from django.contrib import admin
from .models import Movie, Comment


class CommentInline(admin.TabularInline):
    model = Comment
    extra = 0
    min_num = 0
    max_num = 5
    verbose_name = "댓글"
    verbose_name_plural = "댓글들"


@admin.register(Movie)
class MovieModelAdmin(admin.ModelAdmin):
    list_display = ["id", "title", "director", "genre", "release_date", "is_seen"]
    list_filter = ["genre", "release_date", "is_seen"]
    search_fields = ["title", "director"]
    search_help_text = "영화 제목이나 감독으로 검색이 가능합니다"
    list_editable = ["is_seen"]
    inlines = [CommentInline]
    actions = ["change_seen"]

    def change_seen(self, request, queryset):
        for item in queryset:
            item.is_seen = True
            item.save()

    change_seen.short_description = "관람 완료로 변경합니다."
