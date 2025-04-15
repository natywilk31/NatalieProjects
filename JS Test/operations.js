let operations = function() {
  var nextline= "<br/>";
  var first = Number(document.getElementById('first').value);
  var second = Number(document.getElementById('second').value);

  var output = document.getElementById('output');
  var add1 = first + second;
  var sub1 =  first - second;
  var multiply1 = first * second;
  var divide1 = first / second;

  var sum = `${first} + ${second} = ${add1}`;
  var difference = `${first} - ${second} = ${sub1}`;
  var product = ` ${first} * ${second} = ${multiply1}`;
  var quotient = `${first} / ${second} = ${divide1}`;



  output1.textContent = sum;
  output2.textContent = difference;
  output3.textContent = product;
  output4.textContent = quotient;

}