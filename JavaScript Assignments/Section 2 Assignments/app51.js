var nextline= "<br />";

document.write('JS Lab 5.1');
document.write(nextline);
class Contact1 {
  constructor(type, name, lastName, phone){
    this.type = type;
    this.name = name;
    this.lastName = lastName;
    this.phone = phone;
  }
  writeInfo(){
    document.write('Contact Type: ' + this.type);
    document.write(nextline);
    document.write('Contact Name: ' + this.name);
    document.write(nextline);
    document.write('Contact Last Name: ' + this.lastName);
    document.write(nextline);
    document.write('Contact Phone: ' + this.phone);
  }
}

const jeff = new Contact1('Customer', 'Bob', 'Sample' , '999-555-5555');

jeff.writeInfo();


