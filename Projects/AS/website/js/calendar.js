syncData();

// Update the locker header with the chosen location
function updateLockerHeader() {
  let pos = localStorage.getItem('lockerPosition');
  console.log(pos);
  if (pos !== null) {
    document.getElementById("locker-type").textContent = document.getElementById("locker-type").textContent + ' - ' + pos;
    localStorage.removeItem('lockerPosition');
  }
}

updateLockerHeader();

// ----------------------------
// --- Calendar starts here ---
// ----------------------------

const header = document.querySelector(".calendar h3");
const dates = document.querySelector(".dates");
const navs = document.querySelectorAll("#prev, #next");

const months = [
  "January",
  "February",
  "March",
  "April",
  "May",
  "June",
  "July",
  "August",
  "September",
  "October",
  "November",
  "December",
];

let date = new Date();
let month = date.getMonth();
let year = date.getFullYear();
let selectedDates = {};

// Preset of occupied dates
let occupiedDates = {
  '2024-0': [1, 2, 3], // January 2024
  '2024-4': [4, 5, 6], // February 2024
  '2024-5': [7, 8, 9, 10], // December 2023
  // ... and so on for each month and year
};

// Function to check if an array of dates is linear
function isLinear(dates) {
  if (dates.length < 2) {
    return true;
  }
  dates.sort((a, b) => a - b);
  for (let i = 1; i < dates.length; i++) {
    if (dates[i] !== dates[i - 1] + 1) {
      return false;
    }
  }
  return true;
}


function renderCalendar() {
  const start = new Date(year, month, 1).getDay();
  const endDate = new Date(year, month + 1, 0).getDate();
  const end = new Date(year, month, endDate).getDay();
  const endDatePrev = new Date(year, month, 0).getDate();

  let datesHtml = "";

  for (let i = start; i > 0; i--) {
    datesHtml += `<li class="inactive">${endDatePrev - i + 1}</li>`;
  }

  let currentMonthOccupiedDates = occupiedDates[`${year}-${month}`] || [];

  for (let i = 1; i <= endDate; i++) {
    let className =
      i === date.getDate() &&
      month === new Date().getMonth() &&
      year === new Date().getFullYear()
        ? ' class="today"'
        : "";
    if (currentMonthOccupiedDates.includes(i)) {
      className = ' class="red"';
    }

    if (selectedDates.length > 0) {
      className = ' class="selected"';
    }
    datesHtml += `<li${className}>${i}</li>`;
  }

  for (let i = end; i < 6; i++) {
    datesHtml += `<li class="inactive">${i - end + 1}</li>`;
  }

  dates.innerHTML = datesHtml;
  header.textContent = `${months[month]} ${year}`;

  const dateElements = dates.querySelectorAll("li:not(.inactive)");
  selectedDates = {};

  // Event listener for date selection
  dateElements.forEach((el) => {
    el.addEventListener("click", (e) => {
      const day = parseInt(e.target.textContent);
      if (currentMonthOccupiedDates.includes(day)) {
        // If the date is in the occupiedDates array, prevent the selection
        return;
      }
      let currentMonthSelectedDates = selectedDates[`${year}-${month}`] || [];
      if (currentMonthSelectedDates.includes(day)) {
        currentMonthSelectedDates = currentMonthSelectedDates.filter(d => d !== day);
        e.target.classList.remove("selected");
      } else {
        currentMonthSelectedDates.push(day);
        e.target.classList.add("selected");
      }
      selectedDates[`${year}-${month}`] = currentMonthSelectedDates;
      console.log(selectedDates); // This will log the selected dates to the console
    });
  });
}

navs.forEach((nav) => {
  nav.addEventListener("click", (e) => {
    const btnId = e.target.id;

    if (btnId === "prev" && month === 0) {
      year--;
      month = 11;
    } else if (btnId === "next" && month === 11) {
      year++;
      month = 0;
    } else {
      month = btnId === "next" ? month + 1 : month - 1;
    }

    date = new Date(year, month, new Date().getDate());
    year = date.getFullYear();
    month = date.getMonth();

    renderCalendar();
  });
});

// Event listener for button click
document.getElementById("button").addEventListener('click', (e) => {
  let currentMonthSelectedDates = selectedDates[`${year}-${month}`] || [];
  if (!isLinear(currentMonthSelectedDates)) {
    e.preventDefault();
    alert('Selected dates must be linear.');
  } else if (currentMonthSelectedDates.length === 0) {
    e.preventDefault();
    alert('Please select at least one date.');
  } else {
    // Create a string in the format "June 3 - June 10"
    currentMonthSelectedDates.sort((a, b) => a - b);
    let selectedDatesString = `${months[month]} ${currentMonthSelectedDates[0]} - ${months[month]} ${currentMonthSelectedDates[currentMonthSelectedDates.length - 1]}`;

    // Store the selected dates string in localStorage
    localStorage.setItem('selectedDates', selectedDatesString);

    // Store the number of selected dates in localStorage
    localStorage.setItem('numberOfDates', currentMonthSelectedDates.length);

    // Get the locker type from the page
    let lockerType = document.getElementById('locker-type').textContent;

    // Get the locker image on the page
    let imageElements = document.querySelectorAll('img'); // Select all images
    let secondImageElement = imageElements[1]; // Get the second image
    let src = secondImageElement.getAttribute('src'); // Get the src attribute of the second image

    // Store the locker type and image type in localStorage
    localStorage.setItem('locker', lockerType);
    localStorage.setItem('image', src);

    // Send the locker daily price
    let dailyPrice = document.getElementById('price').textContent.split(" ")[0].replace('€', '');
    localStorage.setItem('dailyPrice', dailyPrice);

    // Redirect to the checkout page
    window.location.href = 'checkout.html';
  }
});

renderCalendar();
