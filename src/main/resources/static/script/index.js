const resetClass = (element, classname) => {
  element.classList.remove(classname);
}

const formWrapper = document.querySelector(".form-wrapper");
const form = document.getElementById('auth-form');
const signUpTab = formWrapper.querySelector(".show-signup")
const signInTab = formWrapper.querySelector(".show-signin")

const changeSignUpTab = () => {
  resetClass(formWrapper, "signin");
  formWrapper.classList.add("signup");
  document.getElementById("submit-btn").innerText = "회원가입";

  const authForm = document.getElementById("auth-form");
  authForm.action = "/signup";  // 회원가입 URL

  const inputs = formWrapper.querySelectorAll('input');
  inputs.forEach(input => {
    input.tabIndex = 1;
  });
}

const changeSignIpTab = () => {
  resetClass(formWrapper, "signup");
  formWrapper.classList.add("signin");
  document.getElementById("submit-btn").innerText = "로그인";

  const authForm = document.getElementById("auth-form");
  authForm.action = "/login";  // 로그인 URL

  const hideInputs = document.querySelectorAll('.hide input');
  hideInputs.forEach(input => {
    input.tabIndex = -1;
  });
}

signUpTab.addEventListener("click", changeSignUpTab);
signInTab.addEventListener("click", changeSignIpTab);

form.addEventListener('submit', async (e) => {
  e.preventDefault()

  const formData = new FormData(form)

  // 프론트에서 비밀번호 확인 검증
  const password = formData.get('password')
  const confirmPassword = formData.get('confirmPassword')

  if (confirmPassword && password !== confirmPassword) {
    alert('비밀번호가 일치하지 않습니다.')
    return
  }

  // 서버에는 confirmPassword 보내지 않음
  formData.delete('confirmPassword')

  const action = form.action

  try {
    const res = await fetch(action, {
      method: 'POST',
      body: formData
    })

    const result = await res.json()

    if (res.ok) {
      alert(result.msg || '성공')
      console.log(action)
      console.log(result)
      if(action === `${origin}/login`) {
        localStorage.setItem("name", result.name)
        window.location.href = result.href
      } else {
        changeSignIpTab()
      }
    } else {
      alert(result.error || '실패')
    }

  } catch (error) {
    // console.log(error)
    alert('네트워크 오류가 발생했습니다.')
  }
})

