let toSeconds = function() {
  var hours1 = Number(document.getElementById('hours').value);
  var minutes1 = Number(document.getElementById('minutes').value);

  var output = document.getElementById('output');
  var seconds;
  seconds = hours1*3600;
  seconds += minutes1*60;
  output.textContent = `${hours1} hours and ${minutes1} minutes is ${seconds} seconds`;
}