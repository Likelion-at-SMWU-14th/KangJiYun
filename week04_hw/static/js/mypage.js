function moveToMain() {
  location.href = "/main/";
}

document.getElementById("email").addEventListener("click", function () {
  navigator.clipboard.writeText("kangji09879@sookmyung.ac.kr");
  alert("복사됐어요!");
});
