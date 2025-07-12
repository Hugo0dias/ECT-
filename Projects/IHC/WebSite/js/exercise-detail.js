document.addEventListener('DOMContentLoaded', function() {
  // Get elements
  const elements = {
    checkRecordBtn: document.getElementById('checkRecordBtn'),
    favouriteBtn: document.getElementById('favouriteBtn'),
    toast: document.getElementById('toast'),
    favouriteStatus: document.getElementById('favouriteStatus')
  };

  // Get exercise data from HTML attributes
  const exerciseData = {
    name: document.documentElement.getAttribute('data-name'),
    type: document.documentElement.getAttribute('data-type'),
    img: document.documentElement.getAttribute('data-img'),
    muscles: document.querySelector('.exercise-header p')?.textContent.replace('Primary Muscles:', '').trim()
  };

  // Initialize
  updateFavouriteState();

  // Event listeners
  if (elements.checkRecordBtn) {
    elements.checkRecordBtn.addEventListener('click', handleRecordCheck);
  }

  if (elements.favouriteBtn) {
    elements.favouriteBtn.addEventListener('click', toggleFavourite);
  }

  window.addEventListener('storage', function(event) {
    if (event.key === 'favouriteExercises') {
      updateFavouriteState();
    }
  });

  // Functions
  function handleRecordCheck() {
    const key = `${exerciseData.name.toLowerCase().replace(/\s+/g, '-')}-record`;
    const lastRecord = localStorage.getItem(key) || 'No record found';
    const newRecord = prompt(`Your last record: ${lastRecord}\nEnter new record (e.g., 100kg x 5 reps):`);
    
    if (newRecord) {
      localStorage.setItem(key, newRecord);
      elements.checkRecordBtn.textContent = 'Record Updated!';
      setTimeout(() => {
        elements.checkRecordBtn.textContent = 'Check Record';
      }, 2000);
    }
  }

  function toggleFavourite() {
    let favourites = JSON.parse(localStorage.getItem('favouriteExercises')) || [];
    const index = favourites.findIndex(e => e.name === exerciseData.name);
    
    if (index >= 0) {
      favourites.splice(index, 1);
      showToast('Removed from Favourites 💔');
    } else {
      favourites.push(exerciseData);
      showToast('Added to Favourites ❤️');
    }
    
    localStorage.setItem('favouriteExercises', JSON.stringify(favourites));
    updateFavouriteState();
    window.dispatchEvent(new Event('favouritesUpdated'));
  }

  function updateFavouriteState() {
    const favourites = JSON.parse(localStorage.getItem('favouriteExercises')) || [];
    const isFavourite = favourites.some(e => e.name === exerciseData.name);

    if (elements.favouriteBtn) {
      elements.favouriteBtn.classList.toggle('active', isFavourite);
      elements.favouriteBtn.textContent = isFavourite 
        ? '❤️ ' 
        : '💔 ';
    }

    if (elements.favouriteStatus) {
      elements.favouriteStatus.classList.toggle('hidden', !isFavourite);
    }
  }

  function showToast(message) {
    if (!elements.toast) return;
    
    elements.toast.textContent = message;
    elements.toast.classList.remove('hidden');
    elements.toast.classList.add('show');
    
    setTimeout(() => {
      elements.toast.classList.remove('show');
      setTimeout(() => elements.toast.classList.add('hidden'), 300);
    }, 2000);
  }
});