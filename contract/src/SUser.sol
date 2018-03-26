pragma solidity ^0.4.0;

contract SUser {

    uint public len = 0;

    address[] public orderList;

    mapping(uint => Acc) public accmap;

    struct Acc {
        uint id;
        address adr;
        uint cate;//类型 0-核心 1-上游企业 2-金融机构
        string name;
        uint time;
    }

    function reg(string _name, address _address, uint _cate) public {
        accmap[len++] = Acc(len, _address, _cate, _name, now);
    }

    function saveOrder(address _od) public {
        orderList.push(_od);
    }

    function orderSize() public view returns (uint){
        return orderList.length;
    }
}