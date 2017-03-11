//Sarah Zuckerman
//Mancala Javascript

function initGame()
{

    //initialize  global variables
    
    UNIT_LENGTH = 60;
    selected = 0;

    gameBoard = [4, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4, 4, 4, 0];
    turn = 1;
    document.getElementById('message').innerHTML="pick a pot in the top row you want to play on.";
    document.getElementById('turn').innerHTML="Pick Pot";
    
    makeBoard();

}


function makeBoard()
{
    //width of whole display
    var width = parseInt($("#display").css("width"));
    //minus number is 6 20px pots and 2 30px end pots
    var startX = width/2 - 225;


    //make end pot 1

    var p1Pot = document.createElement("div"); //make players pot on the left
    $(p1Pot).addClass("endPot");
    $(p1Pot).text("you:");
    $(p1Pot).attr("data-stones",'0');
    $(p1Pot).css("left", startX-30 + 'px');
    $(p1Pot).css("top", 75 + 'px');
    $(p1Pot).attr("data-index", '7');
    document.getElementById("display").appendChild(p1Pot);
 
    var cpuPot = document.createElement("div"); //make computers pot on the right
    $(cpuPot).addClass("endPot");
    $(cpuPot).text("cpu:");
    $(cpuPot).attr("data-stones",'0');
    $(cpuPot).css("left", startX+425 + 'px');
    $(cpuPot).css("top", 75 + 'px');
    $(cpuPot).attr("data-index", '14');
    document.getElementById("display").appendChild(cpuPot);

    var p1Stones = document.createElement("div"); //players stones under pot name
    $(p1Stones).addClass("endPotStones");
    $(p1Stones).text($(p1Pot).attr("data-stones"));
    $(p1Stones).attr('name', 'p1Stones');
    $(p1Stones).css("left", startX-15 + 'px');
    $(p1Stones).css("top", 110 + 'px');
    document.getElementById("display").appendChild(p1Stones);

    var cpuStones = document.createElement("div"); //computers stones under pot name
    $(cpuStones).addClass("endPotStones");
    $(cpuStones).text($(cpuPot).attr("data-stones"));
    $(cpuStones).attr('name','p2Stones');
    $(cpuStones).css("left", startX+440 + 'px');
    $(cpuStones).css("top", 110 + 'px');
    document.getElementById("display").appendChild(cpuStones);

    for (var i = 1; i<7; i++)
    {

	var playerBox = document.createElement("div");//div is the type
	$(playerBox).addClass("playerPot");
	$(playerBox).attr("data-selected", "false");;
	$(playerBox).attr("data-index", "" + 7-i);
	$(playerBox).attr("data-stones",  "4");
	$(playerBox).text($(playerBox).attr("data-stones"));
	$(playerBox).css("left", startX+i*UNIT_LENGTH + 'px');
	$(playerBox).css("top", 50  + 'px');
	document.getElementById("display").appendChild(playerBox);


    
	var cpuBox = document.createElement("div");
	
	$(cpuBox).addClass("cpuPot");
	$(cpuBox).attr("data-index", "" +(i+7));
	$(cpuBox).attr("data-stones",  "4");
	$(cpuBox).text($(cpuBox).attr("data-stones"));
	$(cpuBox).css("left", startX+i*UNIT_LENGTH + 'px');
	$(cpuBox).css("top", 110 + 'px');

	document.getElementById("display").appendChild(cpuBox);

    }


    $(".playerPot").click(selectBox);
    

    

}

//click handler
function selectBox()
{
    //get pot number
    var id =  parseInt($(this).attr('data-index'));

    if (selected == 0)
    {
	if ( $(this).attr('data-selected') == 'false' && $(this).attr('data-stones')!='0' && turn==1)
	{
	    $(this).attr('data-selected', 'true');
	    selected = 1;
	  //  document.getElementById('message').innerHTML = gameBoard[id-1];
	}
	else if ( $(this).attr('data-stones') == '0')
	{
	    document.getElementById("message").innerHTML = "You cannot pick a pot with 0 stones, please chose another pot.";
	}
	else if ( id>7)
	{
	    document.getElementById("message").innerHTML = "Please pick a pot on the top row.";
	}
    }
    else if ($(this).attr('data-selected') == 'true')
	{
	    $(this).attr('data-selected', 'false');
	    selected = 0;
	}
}

function playPot()
{
    if (document.getElementById('turn').innerHTML == "Thinking")
	return;
    if (selected == 3)
	{
	    $("#display").empty();
	    initGame();
	}
 
    else if (selected == 1)
    {
	var pot = $("[data-selected=true]").attr('data-index');
	$("[data-selected=true]").attr('data-selected', 'false');
	var pos = updateGameBoard(pot);

	updateGraphic();
       	

	if (pos == 6)
	    {
		document.getElementById('tester').innerHTML = "You moved on pot " + pot;
		document.getElementById('message').innerHTML = "You landed in your own pot! Pick another and go again";
		gameOver();
	    }
	else
	    {
		turn = 2;
		document.getElementById('turn').innerHTML = "AI";
		document.getElementById('message').innerHTML = "Computer's turn.";
		document.getElementById('tester').innerHTML = "You moved on pot " + pot;
		gameOver();
		if (selected!=3)
		    compMove();
	    }
	
    }
    else if (selected == 0 && turn==1)
    {
	document.getElementById('message').innerHTML = "you have to choose a pot to play";
    }
    else if (turn==2 && selected == 0)
	{
	   compMove();
	}


    
}


function updateGameBoard(potNum)
{	      
    var pot = $("[data-index='"+potNum+"']");
    var stones = parseInt(pot.attr('data-stones'));
    gameBoard[potNum-1]=0;
    selected=0;

    for (var i = stones; i>0; i--)
	{
	    gameBoard[potNum]+=1;
	    if (turn == 1)
		{
		    if (potNum == 12)
			potNum = 0;
		    else
			potNum++;
		}
	    else
		{
		    if (potNum == 5)
			potNum = 7;
		    else if (potNum == 13)
			potNum = 0;
		    else
			potNum++;
		}
	}

    potNum=potNum-1;
    var home;   

    
      if ((-1<potNum && potNum<6 && turn == 1) || (6<potNum && potNum<13 && turn == 2))
	{
	    if (gameBoard[potNum]==1)
		{
		    if (turn == 1)
			home = 6;
		    else
			home = 13;
		    gameBoard[potNum]=0;
		    gameBoard[home]=gameBoard[home]+1+gameBoard[12-potNum];
		    gameBoard[12-potNum]=0;
		}
		    
	}
      return potNum;
}


function updateGraphic()
{
    for (i = 1; i<=14; i++)
	{
	    var pot =  $("[data-index='"+i+"']");
	    pot.attr('data-stones', gameBoard[i-1]);
	    if (i!=7 && i!=14)
		{
		    pot.text(pot.attr('data-stones'));
		}
	    else
		{
		    if (i == 7)
			$("[name=p1Stones]").text(pot.attr('data-stones'));
		    else
			$("[name=p2Stones]").text(pot.attr('data-stones'));
		}
	}
}

function compMove()
{
    document.getElementById('turn').innerHTML = "Thinking";
    var board = stringBoard();
    $.getJSON("http://127.0.0.1:4040/computer.html?" + board, doMove);
 
}
 
function doMove(result)
{
    console.log(stringBoard());
    var potNum = result.move;
    var pos = updateGameBoard(potNum);
    updateGraphic();
    console.log(pos);
    if (pos == -1)
	    {
		document.getElementById('tester').innerHTML = "The computer moved on pot " + potNum;
		document.getElementById('message').innerHTML = "The computer gets to go again";
		document.getElementById('turn').innerHTML = "AI";
		gameOver();
		if (selected!=3)
			playPot();
	    }
    else
	    {
		gameOver();
		turn = 1;
		document.getElementById('turn').innerHTML = "Pick Pot";
		document.getElementById('message').innerHTML = "pick a pot in the top row you want to play on.";
		document.getElementById('tester').innerHTML = "The computer moved on pot " + potNum;
	    }
}


function stringBoard()
{
    var board = "";
    for (i = 0; i<14; i++)
	{
	    board = board + gameBoard[i]+",";
	}
    return board;
}
function gameOver()
{
    if (gameBoard[6]>24)
	{
	    document.getElementById('message').innerHTML= "Congrats! You beat the computer! Press the button if you would like to play again";
	    document.getElementById('turn').innerHTML="New Game";
	    //return true;
	    selected = 3;
	}
    else if (gameBoard[13]>24)
	{
	    document.getElementById('message').innerHtml="Uh oh, looks like you lost to the computer. Press the button if you would like to play again";
	    document.getElementById('turn').innerHTML="New Game";
	    //return true;
	    selected=3;
	}
    else
	{
	    var cTotal = 0;
	    var pTotal = 0;
	    for (i = 0; i<6; i++)
		{
		    pTotal=pTotal+gameBoard[i]	;
		    cTotal=cTotal+gameBoard[i+7];
		}
		console.log(cTotal);

		

	    //console.log(cTotal+", " +pTotal);

	    if (cTotal!=0 && pTotal!=0)
		{
		    return;
		}
	    else
		{
		    cTotal=cTotal+gameBoard[13];
		    pTotal=pTotal+gameBoard[6];
		    if (cTotal>pTotal){
				document.getElementById('message').innerHTML= "Uh oh, looks like you lost to the computer. Press the button if you would like to play again";
				document.getElementById('turn').innerHTML="New Game";
				}
		    else if (pTotal>cTotal)
		    {
				document.getElementById('message').innerHTML= "Congrats! You beat the computer! Press the button if you would like to play again";
				document.getElementById('turn').innerHTML="New Game";
				}
		    else
				document.getElementById('message').innerHTML="Looks like you tied the computer. Click the button if you would like to play again.";
		    	document.getElementById('turn').innerHTML="New Game";
		    //return true;
		    selected=3;
		}
		
	}

	
}