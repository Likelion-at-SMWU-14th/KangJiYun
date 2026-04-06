function moveToMain() {
  location.href = "/main/";
}

document.getElementById("email").addEventListener("click", function () {
  const email = this.dataset.email;
  navigator.clipboard.writeText(email);
  alert("복사됐어요!");
});
