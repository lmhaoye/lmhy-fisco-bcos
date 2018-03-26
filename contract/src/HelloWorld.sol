pragma solidity ^0.4.0;

contract HelloWorld {
    string public str = "Hello World";

    event Msg(
        uint _code,
        address indexed _from,
        string _str
    );

    function say() public view returns (string) {
        return str;
    }

    function set(string s) public {
        emit Msg(0,msg.sender,s);
        str = s;
    }
}