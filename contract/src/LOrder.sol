pragma solidity ^0.4.0;
contract LOrder {
    string myCode;
    uint fee;
    string goods;
    uint weight;
    address from;
    address to;
    address go;
    string wlCode;
    string ydCode;
    uint public state; //0开始，1已分配运单 2已发车 3已完成

    uint public ydSize;
    uint public rcSize;
    mapping(uint => Yd) public ydMap;
    mapping(uint => Rc) public rcMap;

    struct Yd {
        string code;
        string carNo;
        uint weight;
        uint state;
    }
    struct Rc {
        string code;
        string reNo;
        uint time;
    }

    function LOrder(string _myCode,uint _fee,string _goods,uint _weight,address _from,address _to,address _go,string _wlCode) public {
        myCode = _myCode;
        fee = _fee;
        goods = _goods;
        weight = _weight;
        from = _from;
        to = _to;
        go = _go;
        wlCode = _wlCode;
        state = 0;
    }

    function geneYd(string _ydCode) public {
        ydCode = _ydCode;
        state =1;
    }

    function geneTs(string _code,string _carNo,uint _weight) public {
        if(state < 2) {
            state = 2;
        }
        ydMap[ydSize++] = Yd(_code,_carNo,_weight,0);
    }

    function toReceipt(string _reNo,string _code) public {
        
        for(uint8 i=0;i<ydSize;i++) {
            Yd memory temp = ydMap[i];
            if( keccak256(temp.code) == keccak256(_reNo) ) {
                ydMap[i].state = 1;
                break;
            }
        }
        rcMap[rcSize++] = Rc(_code,_reNo,now);

        if(ydSize == rcSize) {
            state = 3;
        }
    }

}