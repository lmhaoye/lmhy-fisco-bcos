pragma solidity ^0.4.0;
contract LUser {
    address[] public los;
    uint256 public umapSize;
    mapping(uint256=>User) public umap;

    struct User {
        address adr;
        string name;
        uint256 ty;
    }

    function addOrder(address adr) public {
        los.push(adr);
    }

    function add(address _adr,string _name,uint256 _ty) public {
        umap[umapSize++] = User(_adr,_name,_ty);
    }

    function getByAdr(address adr) public view returns (address,string,uint256) {
        User memory temp;
        for (uint8 i = 0; i < umapSize; i++) {
            if( umap[i].adr == adr ) {
                temp = umap[i];
            }
        }
        return (temp.adr,temp.name,temp.ty);
    }
}