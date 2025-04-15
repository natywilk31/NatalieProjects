function firstWord(sentence) {
  noSpaces = sentence.trim();
  spaceIndex = noSpaces.indexOf(' ');
  ;
  firstW = noSpaces.slice(0,spaceIndex);
  document.write(firstW);
}

firstWord('cat dog horse cow');