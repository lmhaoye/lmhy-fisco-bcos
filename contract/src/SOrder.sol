pragma solidity ^0.4.0;
contract SOrder {
    //订单所有者
    address public from;
    //订单接收方
    address public to;
    //订单状态枚举类型
    enum State { Ready, Created, Locked }
    // 订单状态
    State public state;
    //创建时间
    uint public createTime;

    //订单信息
    Info public info;

    uint public cmapSize;//订单长度
    uint public lmapSize;//贷款订单数量
    uint public rmapSize;//还款订单数量
    //拆分列表
    mapping(uint => Cder) public cmap;
    //贷款列表
    mapping(uint => Loan) public lmap;
    //还款列表
    mapping(uint => Repay) public rmap;

    //订单信息
    struct Info {
        //订单号
        string code;
        uint fee;
    }
    //拆分结构体
    struct Cder {
        string no;
        address from;
        address ow;//所有者
        uint time;//时间
        uint fee;//债权金额
    }
    //贷款结构体
    struct Loan {
        string no;
        string reNo;//对应拆分订单编号
        address from;//放款方
        address to;//收款方
        uint fee;//贷款金额
        uint time;
    }
    //还款结构体
    struct Repay {
        string no;//还款编号
        string reNo;//关联拆分订单编号
        address from;
        address to;
        uint fee;
        uint time;
    }

    function SOrder(string _code,uint _fee,address _from,address _to) public {
        info = Info(_code,_fee);
        cmap[0] = Cder(_code,_from,_to,now,_fee);
        cmapSize++;
        from = _from;
        to = _to;
        state = State.Ready;
        createTime = now;
    }

    function splitOrder(string _no,address _from,address _to,uint _fee) public {
        cmap[cmapSize++] = Cder(_no,_from,_to,now,_fee);
    }
    function loadFun(string _cNo,string _no,address _from,address _to,uint _fee) public {
        uint v = getCder(_cNo);
        Cder memory loadCder = cmap[v];
        //贷款不能大于拆分订单的金额
        if( _fee > loadCder.fee ) {
            return;
        }
        lmap[lmapSize++] = Loan(_no,_cNo,_from,_to,_fee,now);
    }
    function repayFun(string _cNo,string _no,address _from,address _to,uint _fee) public {
        rmap[rmapSize++] = Repay(_cNo,_no,_from,_to,_fee,now);
    }

    function getCder(string _no) public view returns (uint) {
        uint v;
        for (uint8 i = 0; i < cmapSize; i++) {
            Cder memory temp = cmap[i];
            if( keccak256(temp.no) == keccak256(_no) ) {
                v = i;
                break;
            }
        }
        return v;
    }

    function getLoan(string _no) public view returns (uint) {
        uint v;
        for (uint8 i = 0; i < lmapSize; i++) {
            Loan memory temp = lmap[i];
            if( keccak256(temp.reNo) == keccak256(_no) ) {
                v = i;
                break;
            }
        }
        return v;
    }
    function getRepay(string _no) public view returns (uint) {
        uint v;
        for (uint8 i = 0; i < rmapSize; i++) {
            Repay memory temp = rmap[i];
            if( keccak256(temp.reNo) == keccak256(_no) ) {
                v = i;
                break;
            }
        }
        return v;
    }

}