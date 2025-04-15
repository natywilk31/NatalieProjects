let triangleArea = function() {
  var base = Number(document.getElementById('base').value);
  var height = Number(document.getElementById('height').value);

  var output = document.getElementById('output');
  var area = .5*base*height;
  output.textContent = "The area of the triangle is: " + area;
}