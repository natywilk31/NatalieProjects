##
## Natalie Wilkins 4.1.6.13
##
from random import randrange
board = [['1','2','3'],['4','X','6',],['7','8','9']]


def displayBoard(board):
    print('+-------+-------+-------+')
    print('|       |       |       |')
    print('|   ' + board[0][0] +'   |   ' + board[0][1]+'   |   '+board[0][2]+'   |')
    print('|       |       |       |')
    print('+-------+-------+-------+')
    print('|       |       |       |')
    print('|   ' + board[1][0] +'   |   ' + board[1][1]+'   |   '+board[1][2]+'   |')
    print('|       |       |       |')
    print('+-------+-------+-------+')
    print('|       |       |       |')
    print('|   ' + board[2][0] +'   |   ' + board[2][1]+'   |   '+board[2][2]+'   |')
    print('|       |       |       |')
    print('+-------+-------+-------+')
   
   
    
def enterMove(board):
    while True:
        move = int(input("Please enter a valid number in the range (1-9): "))
        if move < 1 or move > 9:
            print("You have entered an invalid number.")
            continue
        elif str(move) not in board[0] and str(move) not in board[1] and str(move) not in board[2]:
            print("That move is not available.")
            continue
        elif move == 1:
            board[0][0] = "O"
        elif move == 2:
            board[0][1] = "O"
        elif move == 3:
            board[0][2] = "O"
        elif move == 4:
            board[1][0] = "O"
        elif move == 6:
            board[1][2] = "O"
        elif move == 7:
            board[2][0] = "O"
        elif move == 8:
            board[2][1] = "O"
        elif move == 9:
            board[2][2] = "O"
            
        break
            


def makeListOfFreeFields(board):
    global freeSquares
    freeSquares = []
    for row in range (0,3):
        for column in range (0,3):
            if board[row][column] == "X" or board[row][column] == "O":
                pass
            else:
                freeSquares.append(([row],[column]))
    print(freeSquares)
    
    
def victoryFor(board, sign):
    if sign == "O":
        print("Checking if you have won...")
    else:
        print("Checking if the computer has won...")
    
    if board[0][0] == sign and board[0][1] == sign and board[0][2] == sign:
        return True
    elif board[1][0] == sign and board[1][1] == sign and board[1][2] == sign:
        return True
    elif board[2][0] == sign and board[2][1] == sign and board[2][2] == sign:
        return True
    elif board[0][0] == sign and board[1][0] == sign and board[2][0] == sign:
        return True   
    elif board[0][1] == sign and board[1][1] == sign and board[2][1] == sign:
        return True
    elif board[0][2] == sign and board[1][2] == sign and board[2][2] == sign:
       return True
    elif board[0][0] == sign and board[1][1] == sign and board[2][2] == sign:
        return True
    elif board[2][0] == sign and board[1][1] == sign and board[0][2] == sign:
        return True 
    else:
        print("No winner yet! Play on!")
        


def drawMove(board):

    while True:
        row = randrange(3)
        column = randrange(3)
        
        if ([row],[column]) not in freeSquares:
            continue
        else:
            board[row][column] = "X"
            return
board = [['1','2','3'],['4','X','6',],['7','8','9']]
numberOfMoves = 1
human = "O"
computer = "X"
print("Welcome to Tic-Tac-Toe!")
print("Here is the current game board.")
displayBoard(board)



while numberOfMoves < 9:
    enterMove(board)
    numberOfMoves += 1
    displayBoard(board)
    
    if victoryFor(board, human) == True:
        print("You beat the computer!")
        break
    else:
        print("Here are the current free spaces and the game board: ")
        makeListOfFreeFields(board)
        print()
        displayBoard(board)
    print()
    print("It is the computer's turn!")
    drawMove(board)
    numberOfMoves += 1
    displayBoard(board)
    print()
    
    if victoryFor(board, computer) == True:
        print("The computer has outsmarted you! Game over!")
        break
    else:
        print("Here are the current free spaces and the game board: ")
        makeListOfFreeFields(board)
        print()
        displayBoard(board)
else:
    print("This game has ended in a tie!")
    
print("Thanks for playing! Play again soon!")
    
