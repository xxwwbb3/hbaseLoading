package com.dinglicom.clouder.xml;

public class FieldNode implements Comparable<FieldNode> {
	
	public enum Option {
		// 利用构造函数传参
		NORMAL(1), CONST(2), SECTION(3), REVERSSE(4);
		// 定义私有变量
		private int m_nCode;

		// 构造函数，枚举类型只能为私有
		private Option(int _nCode) {
			m_nCode = _nCode;
		}

		@Override
		public String toString() {
			return String.valueOf(m_nCode);
		}
	}

	public String getFieldName() {
		return m_fieldName;
	}
	public void setFieldName(String fieldName) {
		m_fieldName = fieldName;
	}
	public int getOrderId() {
		return m_orderId;
	}
	public void setOrderId(int orderId) {
		m_orderId = orderId;
	}
	public String getDefValue() {
		return m_defValue;
	}
	public void setDefValue(String defValue) {
		m_defValue = defValue;
	}
	public Option getOption() {
		return m_option;
	}
	public void setOption(Option option) {
		m_option = option;
	}
	public String getConstValue() {
		return m_constValue;
	}
	public void setConstValue(String constValue) {
		m_constValue = constValue;
	}
	public String getSection() {
		return m_section;
	}
	public void setSection(String section) {
		m_section = section;
	}
	public FieldType getType() {
		return m_type;
	}
	public void setType(String type) {
		if (type == null) {
			return;
		}
		m_type = FieldType.get(type);
	}

	@Override
	public int compareTo(FieldNode o) {
		return m_orderId - o.getOrderId();
	}

	@Override
	public String toString() {
		return "FieldNode [fieldName=" + m_fieldName + ", orderId=" + m_orderId
				+ ", typeName=" + m_type + ", defValue=" + m_defValue + ", option="
				+ m_option + "]\n";
	}
	
	private String m_fieldName = null;
	private int m_orderId = -1;
	private String m_defValue = null;
	private Option m_option = Option.NORMAL;
	private String m_constValue = null;
	private String m_section = null;
	private FieldType m_type = null;

}
