document.addEventListener('DOMContentLoaded', function() {
    // Verifica se há um utilizador logado
    const currentUserEmail = sessionStorage.getItem('currentUser');
    
    if (!currentUserEmail) {
      alert('Please login to view your profile');
      window.location.href = 'login.html';
      return;
    }
  
    // Busca os dados do utilizador
    const users = JSON.parse(localStorage.getItem('logins')) || [];
    const currentUser = users.find(user => user.email === currentUserEmail);
    
    if (currentUser) {
      updateProfileInfo(currentUser);
    } else {
      alert('User data not found');
      window.location.href = 'login.html';
    }
  
    // Adiciona evento de logout
    document.getElementById('logout-btn').addEventListener('click', function() {
      sessionStorage.removeItem('currentUser');
      window.location.href = 'login.html';
    });
  });
  
  function updateProfileInfo(user) {
    document.getElementById('profile-name').textContent = user.name || 'Not specified';
    document.getElementById('profile-email').textContent = user.email;
    
    // Atualiza campos adicionais se existirem no objeto do utilizador
    if (user.age) document.getElementById('profile-age').textContent = user.age;
    if (user.height) document.getElementById('profile-height').textContent = user.height;
    if (user.weight) document.getElementById('profile-weight').textContent = user.weight;
    if (user.favoriteExercise) document.getElementById('profile-favorite').textContent = user.favoriteExercise;
    
    // Adicione mais campos conforme necessário
  }