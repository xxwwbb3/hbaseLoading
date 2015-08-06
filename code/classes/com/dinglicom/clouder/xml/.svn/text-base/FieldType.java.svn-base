/**
 * 
 */
package com.dinglicom.clouder.xml;

/**
 * @author panzhen.jack@gmail.com
 * 
 */
public enum FieldType {

	UBYTE("ubyte", 1), BYTE("byte", 1), USHORT("ushort", 2), SHORT("short", 2), UINT("uint", 4), INT("int", 4), ULONG(
			"ulong", 8), LONG("long", 8), FLOAT("float", 4), DOUBLE("double", 8), STRING("string", 0), DATETIME(
			"datetime", 4), BIN("bin", 0);

	private String typeName = null;
	private int tLength = -1;

	private FieldType(String type, int length) {
		this.typeName = type;
		this.tLength = length;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("type : ");
		sb.append(typeName);
		sb.append(", length : ");
		sb.append(tLength);
		return sb.toString();
	}

	public String getTypeName() {
		return typeName;
	}

	public int getTypeLength() {
		return tLength;
	}

	/**
	 * 
	 * @param typeName
	 * @return
	 */
	public static FieldType get(String type) {
		if (type == null) {
			throw new IllegalArgumentException("typeName is null.");
		}

		FieldType t = null;
		type = type.toLowerCase();

		if ("byte".equals(type)) {
			t = BYTE;
		} else if ("ubyte".equals(type)) {
			t = UBYTE;
		} else if ("short".equals(type)) {
			t = SHORT;
		} else if ("ushort".equals(type)) {
			t = USHORT;
		} else if ("int".equals(type)) {
			t = INT;
		} else if ("uint".equals(type)) {
			t = UINT;
		} else if ("long".equals(type)) {
			t = LONG;
		} else if ("ulong".equals(type)) {
			t = ULONG;
		} else if ("double".equals(type)) {
			t = DOUBLE;
		} else if ("string".equals(type)) {
			t = STRING;
		} else if ("datetime".equals(type)) {
			t = DATETIME;
		} else if ("bin".equals(type)) {
			t = BIN;
		} else {
			throw new IllegalArgumentException("typeName is not defined(" + type + ").");
		}

		return t;
	}
}
