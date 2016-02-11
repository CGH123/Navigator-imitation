package navigator.dataStruct;


import java.util.Vector;


///////////////////////////////////////Trie���ĸ��ڵ�////////////////////////////////
/*
 * �ֵ������ڵ���
 */
class RootNode extends TrieTreeNode{

	//���캯����value�Ǹ��ڵ��ֵ
	public RootNode(char value) {
		super(value);
	}
	
	//���캯����value�Ǹ��ڵ��ֵ��isleaf��ʾ�ýڵ��Ƿ�ΪҶ�ӽڵ�
	public RootNode(char value,boolean isLeaf){
		super(value,isLeaf);
	}
	
	
	//��Ӻ��ӽڵ㣬value�Ǻ��ӽڵ��ֵ
	@Override
	public TrieTreeNode addChildren(char value){
		int index=value-'A';//���ӽڵ��ڵ�ǰ�ڵ�ĺ��������е��±�
		if(children[index]!=null) return children[index];//����Ѿ����ڸú��ӽڵ㣬��ֱ�ӷ���
		
		//���û�У��ʹ����µĽڵ�
		else{
			children[index]=new TrieTreeNode(value);
			return children[index];
		}
	}
	
	
	//�����Ƿ�����valueΪֵ�ĺ��ӽڵ�
	@Override
	public boolean hasChildrenOf(char value){
		int index=value-'A';
		if(index<0||index>=26) return false;
		if(children[index]==null)return false;
		else return true;
	}
	
	//����һ����valueΪֵ�ú��ӽڵ�
	@Override
	public TrieTreeNode childrenOf(char value){
		if(value<'A'||value>'Z') return null;
		else return children[value-'A'];
	}
}

/////////////////////////////////////////////////////////////////////////////////////





////////////////////////////////////////Trie���ϵĽڵ�///////////////////////////////////
/*
 * �ֵ����ڵ���
 */
class TrieTreeNode {
	private char value;//�ڵ��ֵ
	protected TrieTreeNode children[];//���ӽڵ�����
	private boolean leafOrNot;//�Ƿ�Ҷ�ӽڵ�
	private Point leafPoint;//�Ե�ǰ�ڵ���Ϊĩβ�ڵ��Point����
	
	
	//���캯����value�ǽڵ��ֵ��isleaf��ʾ�ýڵ��Ƿ�ΪҶ�ӽڵ�
	public TrieTreeNode(char value,boolean isLeaf){
		
		//��ʼ�����ӽڵ�����
		children=new TrieTreeNode[26];
		for(int i=0;i<26;i++) children[i]=null;
		
		setIsLeaf(isLeaf);
		setValue(value);
		leafPoint=null;
	}
	
	
	//���캯����value�Ǹýڵ��ֵ
	public TrieTreeNode(char value){
		
		//��ʼ�����ӽڵ�����
		children=new TrieTreeNode[26];
		for(int i=0;i<26;i++) children[i]=null;
			
		setIsLeaf(false);
		setValue(value);
		leafPoint=null;
	}
	
	//����һ����valueΪֵ�ú��ӽڵ�
	public TrieTreeNode childrenOf(char value){
		if(value<'a'||value>'z') return null;
		else return children[value-'a'];
	}
	
	
	//�жϸýڵ��Ƿ�ΪҶ�ӽڵ�ĺ���
	public boolean isLeaf(){
		return leafOrNot;
	}
	
	
	//���øýڵ��Ƿ�ΪҶ�ӽڵ�
	public void setIsLeaf(boolean isLeaf){
		leafOrNot=isLeaf;
	}

	
	//���ؽڵ��ֵ
	public char getValue() {
		return value;
	}

	
	//���ýڵ��ֵ
	public void setValue(char value) {
		this.value = value;
	}
	
	//���һ�����ӽڵ����ǰ�ڵ㣬���ӽڵ��ֵ��value
	public TrieTreeNode addChildren(char value){
		int index=value-'a';
		if(children[index]!=null) return children[index];
		else{
			children[index]=new TrieTreeNode(value);
			return children[index];
		}
	}
	
	
	//�ж��Ƿ�����valueΪֵ�ĺ��ӽڵ�
	public boolean hasChildrenOf(char value){
		int index=value-'a';
		if(index<0||index>=26)return false;
		if(children[index]==null)return false;
		else return true;
	}

	
	//�����ǰ�ڵ���Ҷ�ӽڵ㣬����������ڵ���Ϊĩβ�ڵ��Point����
	public Point getLeafPoint() {
		return leafPoint;
	}

	
	//����һ���Ե�ǰ�ڵ���Ϊĩβ�ڵ��Point����
	public void setLeafPoint(Point leafPoint) {
		this.leafPoint = leafPoint;
	}
	
}

///////////////////////////////////////////////////////////////////////////////////////









///////////////////////////////Public Class/////////////////////////////////////////


/*
 * �ֵ�����
 * ����ά���ص�ĵ�������
 * ���ڷ����û���������
 * ���ڵ��ֵ�Ǵ�Щ��ĸ�������ڵ��ֵ��Сд��ĸ
 */
public class TrieTree {
	private RootNode root;//���ڵ�
	
	//�޲������캯��
	public TrieTree()
	{
		root=new RootNode('a');
	}
	
	//��һ��String��Vector��Ϊ�����Ĺ��캯��
	public TrieTree(Vector<String>vec){
		root=new RootNode('a');
		int size=vec.size();
		for(int i=0;i<size;i++)
		{
			insertString(vec.elementAt(i));//��������ÿһ���ַ���
		}
	}
	
	//�ַ������뵽Trie���ĺ���
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
	
	//��һ���ص���뵽Trie���Ĳ��뺯��
	public void insertPoint(Point pnt){
		TrieTreeNode leafTreeNode=insertString(pnt.getName());
		leafTreeNode.setLeafPoint(pnt);
	}
	
	//�ж��Ƿ���ĳ���ַ���
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
	
	//�����Բ����ַ���Ϊǰ׺���ַ�������Vector
	public Vector<Point> result(String str){
		if(str==null) return null;
		Vector<Point>vec=new Vector<Point>();
		boolean flag=true;
		StringBuffer charVec=new StringBuffer();;
		int length=str.length();
		TrieTreeNode ptr=root,tem;
		
		
		//�������ֵ��������Ƿ�����str��Ϊǰ׺���ַ���
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
		
		//û����str��Ϊǰ׺���ַ�������str����Ϊ0����ѯʧ��
		if(!flag||length==0) return vec;
		
		//���ֵ�������������ѣ���ȡ���
		dfs(ptr,charVec,length,vec);
		return vec;
		
	}
	
	//���Ѻ���������result()����
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
		
		//������һ��֮ǰ�����һ���ַ�ɾȥ
		if(saveStr!=null&&saveStr.length()>0)saveStr.deleteCharAt(saveStr.length()-1);
		
		return;
	}
	
}
