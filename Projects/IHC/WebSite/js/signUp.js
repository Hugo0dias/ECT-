let users = JSON.parse(localStorage.getItem('logins')) || [];

window.onload = function() {
  document.getElementById('sign-up').addEventListener('click', function(event) {
    event.preventDefault();
    signUp();
  });
};

function signUp() {
  let name = document.getElementById('name').value;
  let email = document.getElementById('email').value;
  let password = document.getElementById('password').value;
  let age = document.getElementById('age')?.value; // Campo opcional
  let height = document.getElementById('height')?.value; // Campo opcional
  let weight = document.getElementById('weight')?.value; // Campo opcional

  for (let user of users) {
    if (user.email === email) {
      alert("Email already registered!");
      return false;
    }
  }

  // Cria objeto do utilizador com todos os campos
  const newUser = {
    email, 
    password, 
    name, 
    age,
    height,
    weight,
    isAdmin: false, 
    isDistributor: false,
    favoriteExercise: '' // Pode ser preenchido depois
  };

  users.push(newUser);
  localStorage.setItem('logins', JSON.stringify(users));
  alert("Registration successful!");
  window.location.href = 'login.html';
}