let operations = function() {
  var nextline= "<br/>";
  var first = Number(document.getElementById('first').value);
  var second = Number(document.getElementById('second').value);

  var output = document.getElementById('output');

  var quotient = first % second;

  if (quotient == 0) {
    output.textContent = `${first} divided by ${second} divides evenly `;
  } else
  {
    output.textContent = `${first} divided by ${second} does NOT divide evenly `;
  }
 
}