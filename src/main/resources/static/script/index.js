function resetClass(element, classname) {
  element.classList.remove(classname);
}

document.querySelector(".show-signup").addEventListener("click", () => {
  const form = document.querySelector(".form");
  resetClass(form, "signin");
  form.classList.add("signup");
  document.getElementById("submit-btn").innerText = "회원가입";

  const authForm = document.getElementById("auth-form");
  authForm.action = "/signup";  // 회원가입 URL
});

document.querySelector(".show-signin").addEventListener("click", () => {
  const form = document.querySelector(".form");
  resetClass(form, "signup");
  form.classList.add("signin");
  document.getElementById("submit-btn").innerText = "로그인";

  const authForm = document.getElementById("auth-form");
  authForm.action = "/login";  // 로그인 URL
});