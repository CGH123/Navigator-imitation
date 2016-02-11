package navigator.dataStruct;


import java.util.Vector;


///////////////////////////////////////Trie树的根节点////////////////////////////////
/*
 * 字典树根节点类
 */
class RootNode extends TrieTreeNode{

	//构造函数，value是根节点的值
	public RootNode(char value) {
		super(value);
	}
	
	//构造函数，value是根节点的值，isleaf表示该节点是否为叶子节点
	public RootNode(char value,boolean isLeaf){
		super(value,isLeaf);
	}
	
	
	//添加孩子节点，value是孩子节点的值
	@Override
	public TrieTreeNode addChildren(char value){
		int index=value-'A';//孩子节点在当前节点的孩子数组中的下标
		if(children[index]!=null) return children[index];//如果已经存在该孩子节点，就直接返回
		
		//如果没有，就创建新的节点
		else{
			children[index]=new TrieTreeNode(value);
			return children[index];
		}
	}
	
	
	//返回是否含有以value为值的孩子节点
	@Override
	public boolean hasChildrenOf(char value){
		int index=value-'A';
		if(index<0||index>=26) return false;
		if(children[index]==null)return false;
		else return true;
	}
	
	//返回一个以value为值得孩子节点
	@Override
	public TrieTreeNode childrenOf(char value){
		if(value<'A'||value>'Z') return null;
		else return children[value-'A'];
	}
}

/////////////////////////////////////////////////////////////////////////////////////





////////////////////////////////////////Trie树上的节点///////////////////////////////////
/*
 * 字典树节点类
 */
class TrieTreeNode {
	private char value;//节点的值
	protected TrieTreeNode children[];//孩子节点数组
	private boolean leafOrNot;//是否叶子节点
	private Point leafPoint;//以当前节点作为末尾节点的Point对象
	
	
	//构造函数，value是节点的值，isleaf表示该节点是否为叶子节点
	public TrieTreeNode(char value,boolean isLeaf){
		
		//初始化孩子节点数组
		children=new TrieTreeNode[26];
		for(int i=0;i<26;i++) children[i]=null;
		
		setIsLeaf(isLeaf);
		setValue(value);
		leafPoint=null;
	}
	
	
	//构造函数，value是该节点的值
	public TrieTreeNode(char value){
		
		//初始化孩子节点数组
		children=new TrieTreeNode[26];
		for(int i=0;i<26;i++) children[i]=null;
			
		setIsLeaf(false);
		setValue(value);
		leafPoint=null;
	}
	
	//返回一个以value为值得孩子节点
	public TrieTreeNode childrenOf(char value){
		if(value<'a'||value>'z') return null;
		else return children[value-'a'];
	}
	
	
	//判断该节点是否为叶子节点的函数
	public boolean isLeaf(){
		return leafOrNot;
	}
	
	
	//设置该节点是否为叶子节点
	public void setIsLeaf(boolean isLeaf){
		leafOrNot=isLeaf;
	}

	
	//返回节点的值
	public char getValue() {
		return value;
	}

	
	//设置节点的值
	public void setValue(char value) {
		this.value = value;
	}
	
	//添加一个孩子节点给当前节点，孩子节点的值是value
	public TrieTreeNode addChildren(char value){
		int index=value-'a';
		if(children[index]!=null) return children[index];
		else{
			children[index]=new TrieTreeNode(value);
			return children[index];
		}
	}
	
	
	//判断是否有以value为值的孩子节点
	public boolean hasChildrenOf(char value){
		int index=value-'a';
		if(index<0||index>=26)return false;
		if(children[index]==null)return false;
		else return true;
	}

	
	//如果当前节点是叶子节点，返回以这个节点作为末尾节点的Point对象
	public Point getLeafPoint() {
		return leafPoint;
	}

	
	//设置一个以当前节点作为末尾节点的Point对象
	public void setLeafPoint(Point leafPoint) {
		this.leafPoint = leafPoint;
	}
	
}

///////////////////////////////////////////////////////////////////////////////////////









///////////////////////////////Public Class/////////////////////////////////////////


/*
 * 字典树类
 * 用来维护地点的地名集合
 * 用于方便用户检索地名
 * 根节点的值是大些字母，其他节点的值是小写字母
 */
public class TrieTree {
	private RootNode root;//根节点
	
	//无参数构造函数
	public TrieTree()
	{
		root=new RootNode('a');
	}
	
	//以一个String的Vector作为参数的构造函数
	public TrieTree(Vector<String>vec){
		root=new RootNode('a');
		int size=vec.size();
		for(int i=0;i<size;i++)
		{
			insertString(vec.elementAt(i));//迭代插入每一个字符串
		}
	}
	
	//字符串插入到Trie树的函数
	private TrieTreeNode insertString(String str)
	{
		int length=str.length();
		TrieTreeNode ptr=root;
		for(int i=0;i<length;i++)
		{
			ptr=ptr.addChildren(str.charAt(i));
		}
		ptr.setIsLeaf(true);
		return ptr;
	}
	
	//把一个地点插入到Trie树的插入函数
	public void insertPoint(Point pnt){
		TrieTreeNode leafTreeNode=insertString(pnt.getName());
		leafTreeNode.setLeafPoint(pnt);
	}
	
	//判断是否含有某个字符串
	public boolean ifHas(String str){
		boolean flag=true;
		int length=str.length();
		TrieTreeNode ptr=root;
		for(int i=0;i<length;i++)
		{
			if(ptr.hasChildrenOf(str.charAt(i))==false){
				flag=false;
				break;
			}
			else ptr=ptr.childrenOf(str.charAt(i));
		}
		if(!flag) return false;
		else if(!ptr.isLeaf()) return false;
		else return true;
	}
	
	//返回以参数字符串为前缀的字符串集合Vector
	public Vector<Point> result(String str){
		if(str==null) return null;
		Vector<Point>vec=new Vector<Point>();
		boolean flag=true;
		StringBuffer charVec=new StringBuffer();;
		int length=str.length();
		TrieTreeNode ptr=root,tem;
		
		
		//检验在字典树里面是否有以str作为前缀的字符串
		for(int i=0;i<length;i++)
		{
			if(ptr.hasChildrenOf(str.charAt(i))==false){
				flag=false;
				break;
			}
			else 
			{
				charVec.append(str.charAt(i));
				ptr=ptr.childrenOf(str.charAt(i));
			}
		}
		
		//没有以str作为前缀的字符串或者str长度为0，查询失败
		if(!flag||length==0) return vec;
		
		//在字典树上面进行深搜，获取结果
		dfs(ptr,charVec,length,vec);
		return vec;
		
	}
	
	//深搜函数，用于result()函数
	private void dfs(TrieTreeNode curNode,StringBuffer saveStr,int index,Vector<Point>vec)
	{
		if(curNode.isLeaf()) vec.add(curNode.getLeafPoint());
		for(int i=0;i<26;i++)
		{
			if(curNode.hasChildrenOf((char)('a'+i)))
			{
				saveStr.append( (char)('a'+i));
				dfs(curNode.childrenOf((char)('a'+i)),saveStr,index+1,vec);
			}
				
		}
		
		//返回上一级之前把最后一个字符删去
		if(saveStr!=null&&saveStr.length()>0)saveStr.deleteCharAt(saveStr.length()-1);
		
		return;
	}
	
}
