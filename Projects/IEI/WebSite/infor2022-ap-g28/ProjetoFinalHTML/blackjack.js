var dealerSum = 0;
var yourSum = 0;
var dealerSumShown = 0;

var dealerAceCount = 0;
var yourAceCount = 0; 

var hidden;
var deck;

var canHit = true; //allows the player (you) to draw while yourSum <= 21

window.onload = function() {
    buildDeck();
    shuffleDeck();
    startGame();
}



function buildDeck() {
    let values = ["A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"];
    let types = ["C", "D", "H", "S"];
    deck = [];

    for (let i = 0; i < types.length; i++) {
        for (let j = 0; j < values.length; j++) {
            deck.push(values[j] + "-" + types[i]); //A-C -> K-C, A-D -> K-D
        }
    }
     console.log(deck);
}

function shuffleDeck() {
    for (let i = 0; i < deck.length; i++) {
        let j = Math.floor(Math.random() * deck.length); // (0-1) * 52 => (0-51.9999)
        let temp = deck[i];
        deck[i] = deck[j];
        deck[j] = temp;
    }
    console.log(deck);
}

function startGame() {
    hidden = deck.pop();

    dealerSum += getValue(hidden);
    dealerAceCount += checkAce(hidden); // check ás do dealer
    // console.log(hidden);
    // console.log(dealerSum);
 
    dealerSumShown = dealerSum - getValue(hidden);
    console.log(dealerSum)

    console.log("inicio"+dealerSumShown)




    while (dealerSum < 17) {
        //<img src="./cards/4-C.png">
        let cardImg = document.createElement("img");
        let card = deck.pop();
        cardImg.src = "./cards/" + card + ".png";
        dealerSum += getValue(card);
        dealerSumShown += getValue(card) // valor da carta inicial

        console.log("card"+getValue(card))
        document.getElementById("dealer-sum").innerText = dealerSumShown;





        dealerAceCount += checkAce(card);
        document.getElementById("dealer-cards").append(cardImg);
    }
    console.log("dealer"+dealerSum);

    for (let i = 0; i < 2; i++) {
        let cardImg = document.createElement("img");
        let card = deck.pop();
        cardImg.src = "./cards/" + card + ".png";
        yourSum += getValue(card);
        yourAceCount += checkAce(card);
        document.getElementById("your-cards").append(cardImg);
        
    }

    console.log(yourSum);



    document.getElementById("hit").addEventListener("click", hit);
    document.getElementById("stay").addEventListener("click", stay);

    
 
    document.getElementById("your-sum").innerText = yourSum;
}

function hit() {

    if (!canHit) {
        return;
    }
    document.getElementById("dealer-sum").innerText = dealerSumShown;

    let cardImg = document.createElement("img");
    let card = deck.pop();
    cardImg.src = "./cards/" + card + ".png";
    yourSum += getValue(card);
    console.log(yourSum);
    yourAceCount += checkAce(card);
    document.getElementById("your-cards").append(cardImg);

    document.getElementById("your-sum").innerText = yourSum;


    if (reduceAce(yourSum, yourAceCount) > 21) { //A, J, 8 -> 1 + 10 + 8
        canHit = false;
    }

    if (yourSum == 21) {
        message = "You Win! Blackjack!"
    }

    if (yourSum > 21) {
    message = "You Lose!"
   }
   document.getElementById("hidden").src = "./cards/" + hidden + ".png";

   document.getElementById("dealer-sum").innerText = dealerSum;
   document.getElementById("your-sum").innerText = yourSum;

   $("#dialog").html(message).dialog({
    buttons: {
      Fechar: function() {
        $(this).dialog("close");
        location.reload();
      }
    }
  });



}
    

function stay() {
    dealerSum = reduceAce(dealerSum, dealerAceCount);
    yourSum = reduceAce(yourSum, yourAceCount);

    canHit = false;
    document.getElementById("hidden").src = "./cards/" + hidden + ".png";

    document.getElementById("dealer-sum").innerText = dealerSum;
    document.getElementById("your-sum").innerText = yourSum;

    let message = "";


    if (yourSum > 21) {
    message = "You Lose!";
    } 
   
   else if (dealerSum > 21) {
    message = "You win!";

    }
    //both you and dealer <= 21
    else if (yourSum == dealerSum) {
    message = "Tie!";


    } else if (yourSum > dealerSum && yourSum == 21)  {
    message = "You win! Blackjack!";

    } else if (yourSum > dealerSum)  {
    message = "You win!";
  

    } else if (yourSum < dealerSum) {
    message = "You Lose!";

    }

    $("#dialog").html(message).dialog({
        buttons: {
          Fechar: function() {
            $(this).dialog("close");
            location.reload();
          }
        }
    });
      


    document.getElementById("dealer-sum").innerText = dealerSum;
    document.getElementById("your-sum").innerText = yourSum;
    // document.getElementById("results").innerText = message;
}




function getValue(card) {
    let data = card.split("-"); // "4-C" -> ["4", "C"]
    let value = data[0];

    if (isNaN(value)) { //A J Q K
        if (value == "A") {
            return 11;
        }
        return 10;
    }
    return parseInt(value);
}

function checkAce(card) {
    if (card[0] == "A") {
        return 1;
    }
    return 0;
}

function reduceAce(playerSum, playerAceCount) {
    while (playerSum > 21 && playerAceCount > 0) {
        playerSum -= 10;
        playerAceCount -= 1;
    }
    return playerSum;
}



