

let users = JSON.parse(localStorage.getItem('logins')) || [];

window.onload = function() {
  document.getElementById('login').addEventListener('click', function(event) {
    event.preventDefault();
    login();
  });
};

function checkLogin(email, password) {
  for (let user of users) {
    if (user.email === email && user.password === password) {
      sessionStorage.setItem('currentUser', email);
      return true;
    }
  }
  return false;
}

function login() {
  let email = document.getElementById('email').value;
  let password = document.getElementById('password').value;
  if (checkLogin(email, password)) {
    console.log('Login successful');
    window.location.href = 'index.html';
  } else {
    alert('Login failed');
  }
}

