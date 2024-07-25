// Load the logins from localStorage (if any)
// All logins will be stored in localStorage!
syncData();

// Variable to get/store users
let users = JSON.parse(localStorage.getItem('logins'));

// Listen for the sign-up button click
window.onload = function() {
  document.getElementById('sign-up').addEventListener('click', function(event) {
    event.preventDefault(); // Prevent the form from submitting normally
    signUp();
    // Redirect to the login page
    window.location.href = 'login.html';
  });
};

function signUp() {
  // Get the form values
  let name = document.getElementById('name').value;
  let email = document.getElementById('email').value;
  let password = document.getElementById('password').value;

  // Check if the email is already in use
  for (let user of users) {
    if (user.email === email) {
      return false; // Email is already in use
    }
  }

  // Add the new user to the JSON object
  users.push({email: email, password: password, name: name, isAdmin: false, isDistributor: false});
  console.log(users);

  // Update the JSON data in localStorage
  localStorage.setItem('logins', JSON.stringify(users));
}
