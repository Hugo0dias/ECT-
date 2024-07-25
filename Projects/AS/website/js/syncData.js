async function syncData(){
  let users;
  let storedLogins = localStorage.getItem('logins');

  // Get the current user's email
  let currentUser = sessionStorage.getItem('currentUser');
  console.log(currentUser);

  // Load the logins from localStorage (if any)
  if (storedLogins) {
    console.log('Logins already loaded');
  } else {
    // Use the fetch API to get the JSON file
    await fetch('../db/logins.json')
      .then(response => response.json()) // Parse the data as JSON
      .then(data => {
        // Now data is the parsed JSON object from the file
        console.log(data);
        // Go one level deeper to get the array of users
        users = data.logins;
        console.log(users);
        // Store the logins in localStorage
        localStorage.setItem('logins', JSON.stringify(users));
      })
      .catch(error => console.error('Error:', error));
  }
}
