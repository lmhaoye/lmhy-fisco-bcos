pragma solidity ^0.4.10;

contract RunData {
    string say = "Hello RunData";
    uint public size;

    mapping(uint=>string) public map;

    event Click(address indexed _from,uint indexed id,string con);

    function getSay() public view returns (string) {
        return say;
    }

    function setSay(string _s) public {
        say = _s;
    }

    function getMap(uint key) public view returns (string) {
        return map[key];
    }
    function setMap(string _s) public {
        map[size++] = _s;
        Click(msg.sender,size,_s);
    }

}