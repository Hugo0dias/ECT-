// Clear user's selected dates from local storage when they leave the page
window.onbeforeunload = function() {
    localStorage.removeItem('selectedDates');
    localStorage.removeItem('numberOfDates');
    localStorage.removeItem('locker');
    localStorage.removeItem('image');
    localStorage.removeItem('dailyPrice');
}

console.log("dates", localStorage.getItem('selectedDates'));
console.log("days", localStorage.getItem('numberOfDates'));
console.log("locker", localStorage.getItem('locker'));
console.log("image", localStorage.getItem('image'));
console.log("dailyPrice", localStorage.getItem('dailyPrice'));

// put in let all the variables that are in the local storage
let selectedDates = localStorage.getItem('selectedDates');
let numberOfDates = localStorage.getItem('numberOfDates');
let locker = localStorage.getItem('locker');
let image = localStorage.getItem('image');
let dailyPrice = localStorage.getItem('dailyPrice');
let currentUser = sessionStorage.getItem('currentUser');

// Display the daily price on the page
document.getElementById('unit').textContent = dailyPrice + ' € ';


// Display the selected dates, number of dates, locker type, and locker image on the page
document.getElementById('dates').textContent = selectedDates;
document.getElementById('unit').textContent = document.getElementById('unit').textContent + ' x ' + numberOfDates;
document.getElementById('locker-image').src = image;
document.getElementById('locker-type').textContent = locker;


// Static data for client cards and promo codes
let clientCards = [ 12345, 23456, 34567 ];
let promoCodes = [ 'DISCOUNT', 'PROMO', 'SALE' ];

// Display the subtotal and total on the page
document.getElementById('subtotal').textContent = document.getElementById('subtotal').textContent.split(' ')[0]
  + ' ' + (Number(dailyPrice) * Number(numberOfDates)).toFixed(2) + ' €';
document.getElementById('total').textContent = document.getElementById('total').textContent.split(' ')[0]
  + ' ' + (Number(dailyPrice) * Number(numberOfDates)).toFixed(2) + ' €';

let cardFlag = false;
let promoFlag = false;

document.getElementById('submit-card').addEventListener('click', function() {
    let enteredCard = document.getElementById('client-card').value;
    let notification = document.getElementById('card-notification');
    if (clientCards.includes(Number(enteredCard)) && !cardFlag) {
        notification.textContent = 'Client card is valid.';
        notification.style.color = 'green';
        cardFlag = true;
        document.getElementById('total').textContent = document.getElementById('total').textContent.split(' ')[0]
        + ' ' + (Number(dailyPrice) * Number(numberOfDates) * 0.95).toFixed(2) + ' €';
    } else {
        notification.textContent = 'Client card is invalid.';
        notification.style.color = 'red';
    }
});

document.getElementById('submit-code').addEventListener('click', function() {
    let enteredCode = document.getElementById('promo-code').value;
    let notification = document.getElementById('code-notification');
    if (promoCodes.includes(enteredCode) && !promoFlag) {
        notification.textContent = 'Promo code is valid.';
        notification.style.color = 'green';
        promoFlag = true;
        document.getElementById('total').textContent = document.getElementById('total').textContent.split(' ')[0]
          + ' ' + (Number(dailyPrice) * Number(numberOfDates) * 0.9).toFixed(2) + ' €';
    } else {
        notification.textContent = 'Promo code is invalid.';
        notification.style.color = 'red';
    }
});

function showPaymentFields() {
    const paypalField = document.getElementById('paypal-field');
    const cardFields = document.getElementById('card-fields');
    const paymentType = document.querySelector('input[name="payment"]:checked').value;

    paypalField.style.display = 'none';
    cardFields.style.display = 'none';

    if (paymentType === 'PayPal') {
        paypalField.style.display = 'block';
    } else if (paymentType === 'credit-card') {
        cardFields.style.display = 'block';
    }
}

document.addEventListener('DOMContentLoaded', () => {
    const paymentOptions = document.querySelectorAll('input[name="payment"]');
    paymentOptions.forEach(option => {
        option.addEventListener('change', showPaymentFields);
    });
});

// Select the "Pay" button
let payButton = document.getElementById('submit');

// Add an event listener to the "Pay" button
payButton.addEventListener('click', function(event) {
    // Prevent the button's default action
    event.preventDefault();

    // Select all input fields in the payment section
    let inputFields = document.querySelectorAll('input[required]');

    // Initialize the flag as false
    let isEmptyFieldFound = false;

    // Iterate over each field
    inputFields.forEach(field => {
        // Check if the field is empty
        if (field.value.trim() === '') {
            // If there's already an alert message, don't add another one
            if (field.nextSibling && field.nextSibling.tagName === 'P') {
                return;
            }

            // Create a new element for the alert message
            let alertMessage = document.createElement('p');
            alertMessage.textContent = 'This field is required.';
            alertMessage.style.color = 'red';

            // Append the alert message below the current field
            field.parentNode.insertBefore(alertMessage, field.nextSibling);

            // Set the flag to true
            isEmptyFieldFound = true;
        // If the field is not empty and there's an alert message, remove it
        } else if (field.nextSibling && field.nextSibling.tagName === 'P'){
            field.parentNode.removeChild(field.nextSibling);
        }
    });

    // If the flag is true, stop the form submission
    if (isEmptyFieldFound) {
        return;
    }

    event.preventDefault();
    if (currentUser === null) {
        alert('Please log in to complete the payment.');
        return;
    }

    let lockerStr = locker.split(" ");
    alert('Your locker reservation of ' + lockerStr[0] + ' ' + lockerStr [1]
      + ' in '  + lockerStr[3] + ' ' + lockerStr[4] + ' between '
      + selectedDates + ' has been successfully reserved.');
});
