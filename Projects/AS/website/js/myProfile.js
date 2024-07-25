// Load the logins from localStorage (if any)
// All logins will be stored in localStorage!
syncData();

if (sessionStorage.getItem('currentUser') === null) {
  alert('You must be logged in first!');
  window.history.back();
}

let users = JSON.parse(localStorage.getItem('logins'));

// Get the current user's email
let currentUser = sessionStorage.getItem('currentUser');
console.log(currentUser);

// Fill forms on page load
window.onload = function() { getInfo(); }

// Listen for the log out button click
document.getElementById('logout').addEventListener('click', function(event) {
  event.preventDefault(); // Prevent the form from submitting normally
  logOut();
});

// Listen for the cancel button click
document.getElementById('revert').addEventListener('click', function(event) {
  event.preventDefault(); // Prevent the form from submitting normally
  // Redirect to the login page
  window.history.back();
});

// Begin the process of updating the user's profile
// Listen for the save button click
document.getElementById('save').addEventListener('click', function(event) {
  event.preventDefault(); // Prevent the form from submitting normally
  if (updateProfile()) {
    // Redirect to the login page
    window.location.href = 'lockers.html';
  } else {
    alert('Email is already in use');
  }
});

// Fill the page with the user's profile information
function getInfo() {
  // Get the current user's email
  let currentUser = sessionStorage.getItem('currentUser');
  console.log(currentUser);

  // Get the user's profile
  for (let user of users) {
    if (user.email === currentUser) {
      document.getElementById('name').value = user.name;
      document.getElementById('email').value = user.email;
      document.getElementById('bio').value = user.bio;
    }
  }
}

function updateProfile() {
  // Get the form values
  let name = document.getElementById('name').value;
  let password;

  if (document.getElementById('password').value === '') {
    for (let user of users) {
      if (user.email === currentUser) {
        password = user.password;
      }
  }
  } else {
    password = document.getElementById('password').value;
  }

  let bio = document.getElementById('bio').value;

  // Update the user's profile
  for (let user of users) {
    if (user.email === currentUser) {
      user.name = name;
      user.password = password;
      user.bio = bio;
    }
  }

  // Update the JSON data in localStorage
  localStorage.setItem('logins', JSON.stringify(users));
  return true;
}

function logOut() {
  // Remove the current user from sessionStorage
  sessionStorage.removeItem('currentUser');
  window.location.href = 'index.html';
}