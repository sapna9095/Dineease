const loginForm = document.getElementById('loginForm');
const usernameInput = document.getElementById('username');
const passwordInput = document.getElementById('password');
const loginBtn = document.getElementById('loginBtn');
const togglePasswordBtn = document.querySelector('.toggle-password');

lucide.createIcons({
  attrs: {
    strokeWidth: 1.8,
  },
});

function setFieldError(fieldId, message) {
  const errorEl = document.getElementById(`${fieldId}Error`);
  const field = document.getElementById(fieldId);

  field.setAttribute('aria-invalid', message ? 'true' : 'false');
  errorEl.textContent = message || '';
}

function validateForm() {
  let isValid = true;

  const usernameValue = usernameInput.value.trim();
  const passwordValue = passwordInput.value.trim();

  if (!usernameValue) {
    setFieldError('username', 'Username is required.');
    isValid = false;
  } else {
    setFieldError('username', '');
  }

  if (!passwordValue) {
    setFieldError('password', 'Password is required.');
    isValid = false;
  } else {
    setFieldError('password', '');
  }

  return isValid;
}

function handleLogin(event) {
  event.preventDefault();

  const isValid = validateForm();
  if (!isValid) return;

  loginBtn.disabled = true;
  loginBtn.innerHTML = '<span class="btn-arrow"><i data-lucide="loader-circle"></i></span><span>Logging in...</span>';
  lucide.createIcons({
    attrs: {
      strokeWidth: 1.8,
    },
  });

  const credentials = {
    username: usernameInput.value.trim(),
    password: passwordInput.value.trim(),
    remember: document.querySelector('input[name="remember"]').checked,
  };

  console.log('DineEase AI login request:', credentials);

  setTimeout(() => {
    loginBtn.disabled = false;
    loginBtn.innerHTML = '<span class="btn-arrow"><i data-lucide="arrow-right"></i></span><span>Login</span>';
    lucide.createIcons({
      attrs: {
        strokeWidth: 1.8,
      },
    });
    // Redirect to the web dashboard view
    window.location.href = 'dashboard.html';
  }, 800);
}

loginForm.addEventListener('submit', handleLogin);

if (togglePasswordBtn) {
  togglePasswordBtn.addEventListener('click', () => {
    const isPassword = passwordInput.type === 'password';
    passwordInput.type = isPassword ? 'text' : 'password';
    togglePasswordBtn.setAttribute('aria-label', isPassword ? 'Hide password' : 'Show password');
    togglePasswordBtn.setAttribute('aria-pressed', String(isPassword));
    togglePasswordBtn.innerHTML = isPassword
      ? '<i data-lucide="eye-off"></i>'
      : '<i data-lucide="eye"></i>';
    lucide.createIcons({
      attrs: {
        strokeWidth: 1.8,
      },
    });
  });
}
