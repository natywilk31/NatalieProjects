
var nextline= "<br />";
document.write("JS Labs 2.6")
document.write(nextline);
function greeting(name= "Bob") {
  document.write("Hello, " + name);             
}



function addus(a = 1, b = 2) {
  document.write(`${a} + ${b} = ${a+b}`)            
}

greeting("Natalie");  
document.write(nextline);
document.write(nextline);
addus(3, 5);  
