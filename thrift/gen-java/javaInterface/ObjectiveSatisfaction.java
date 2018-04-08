/**
 * Autogenerated by Thrift Compiler (0.11.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package javaInterface;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.11.0)", date = "2018-04-08")
public class ObjectiveSatisfaction implements org.apache.thrift.TBase<ObjectiveSatisfaction, ObjectiveSatisfaction._Fields>, java.io.Serializable, Cloneable, Comparable<ObjectiveSatisfaction> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ObjectiveSatisfaction");

  private static final org.apache.thrift.protocol.TField OBJECTIVE_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("objective_name", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField SATISFACTION_FIELD_DESC = new org.apache.thrift.protocol.TField("satisfaction", org.apache.thrift.protocol.TType.DOUBLE, (short)2);
  private static final org.apache.thrift.protocol.TField WEIGHT_FIELD_DESC = new org.apache.thrift.protocol.TField("weight", org.apache.thrift.protocol.TType.DOUBLE, (short)3);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new ObjectiveSatisfactionStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new ObjectiveSatisfactionTupleSchemeFactory();

  public java.lang.String objective_name; // required
  public double satisfaction; // required
  public double weight; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    OBJECTIVE_NAME((short)1, "objective_name"),
    SATISFACTION((short)2, "satisfaction"),
    WEIGHT((short)3, "weight");

    private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // OBJECTIVE_NAME
          return OBJECTIVE_NAME;
        case 2: // SATISFACTION
          return SATISFACTION;
        case 3: // WEIGHT
          return WEIGHT;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(java.lang.String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final java.lang.String _fieldName;

    _Fields(short thriftId, java.lang.String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public java.lang.String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __SATISFACTION_ISSET_ID = 0;
  private static final int __WEIGHT_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.OBJECTIVE_NAME, new org.apache.thrift.meta_data.FieldMetaData("objective_name", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SATISFACTION, new org.apache.thrift.meta_data.FieldMetaData("satisfaction", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.WEIGHT, new org.apache.thrift.meta_data.FieldMetaData("weight", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ObjectiveSatisfaction.class, metaDataMap);
  }

  public ObjectiveSatisfaction() {
  }

  public ObjectiveSatisfaction(
    java.lang.String objective_name,
    double satisfaction,
    double weight)
  {
    this();
    this.objective_name = objective_name;
    this.satisfaction = satisfaction;
    setSatisfactionIsSet(true);
    this.weight = weight;
    setWeightIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ObjectiveSatisfaction(ObjectiveSatisfaction other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetObjective_name()) {
      this.objective_name = other.objective_name;
    }
    this.satisfaction = other.satisfaction;
    this.weight = other.weight;
  }

  public ObjectiveSatisfaction deepCopy() {
    return new ObjectiveSatisfaction(this);
  }

  @Override
  public void clear() {
    this.objective_name = null;
    setSatisfactionIsSet(false);
    this.satisfaction = 0.0;
    setWeightIsSet(false);
    this.weight = 0.0;
  }

  public java.lang.String getObjective_name() {
    return this.objective_name;
  }

  public ObjectiveSatisfaction setObjective_name(java.lang.String objective_name) {
    this.objective_name = objective_name;
    return this;
  }

  public void unsetObjective_name() {
    this.objective_name = null;
  }

  /** Returns true if field objective_name is set (has been assigned a value) and false otherwise */
  public boolean isSetObjective_name() {
    return this.objective_name != null;
  }

  public void setObjective_nameIsSet(boolean value) {
    if (!value) {
      this.objective_name = null;
    }
  }

  public double getSatisfaction() {
    return this.satisfaction;
  }

  public ObjectiveSatisfaction setSatisfaction(double satisfaction) {
    this.satisfaction = satisfaction;
    setSatisfactionIsSet(true);
    return this;
  }

  public void unsetSatisfaction() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __SATISFACTION_ISSET_ID);
  }

  /** Returns true if field satisfaction is set (has been assigned a value) and false otherwise */
  public boolean isSetSatisfaction() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __SATISFACTION_ISSET_ID);
  }

  public void setSatisfactionIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __SATISFACTION_ISSET_ID, value);
  }

  public double getWeight() {
    return this.weight;
  }

  public ObjectiveSatisfaction setWeight(double weight) {
    this.weight = weight;
    setWeightIsSet(true);
    return this;
  }

  public void unsetWeight() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __WEIGHT_ISSET_ID);
  }

  /** Returns true if field weight is set (has been assigned a value) and false otherwise */
  public boolean isSetWeight() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __WEIGHT_ISSET_ID);
  }

  public void setWeightIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __WEIGHT_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case OBJECTIVE_NAME:
      if (value == null) {
        unsetObjective_name();
      } else {
        setObjective_name((java.lang.String)value);
      }
      break;

    case SATISFACTION:
      if (value == null) {
        unsetSatisfaction();
      } else {
        setSatisfaction((java.lang.Double)value);
      }
      break;

    case WEIGHT:
      if (value == null) {
        unsetWeight();
      } else {
        setWeight((java.lang.Double)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case OBJECTIVE_NAME:
      return getObjective_name();

    case SATISFACTION:
      return getSatisfaction();

    case WEIGHT:
      return getWeight();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case OBJECTIVE_NAME:
      return isSetObjective_name();
    case SATISFACTION:
      return isSetSatisfaction();
    case WEIGHT:
      return isSetWeight();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof ObjectiveSatisfaction)
      return this.equals((ObjectiveSatisfaction)that);
    return false;
  }

  public boolean equals(ObjectiveSatisfaction that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_objective_name = true && this.isSetObjective_name();
    boolean that_present_objective_name = true && that.isSetObjective_name();
    if (this_present_objective_name || that_present_objective_name) {
      if (!(this_present_objective_name && that_present_objective_name))
        return false;
      if (!this.objective_name.equals(that.objective_name))
        return false;
    }

    boolean this_present_satisfaction = true;
    boolean that_present_satisfaction = true;
    if (this_present_satisfaction || that_present_satisfaction) {
      if (!(this_present_satisfaction && that_present_satisfaction))
        return false;
      if (this.satisfaction != that.satisfaction)
        return false;
    }

    boolean this_present_weight = true;
    boolean that_present_weight = true;
    if (this_present_weight || that_present_weight) {
      if (!(this_present_weight && that_present_weight))
        return false;
      if (this.weight != that.weight)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetObjective_name()) ? 131071 : 524287);
    if (isSetObjective_name())
      hashCode = hashCode * 8191 + objective_name.hashCode();

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(satisfaction);

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(weight);

    return hashCode;
  }

  @Override
  public int compareTo(ObjectiveSatisfaction other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetObjective_name()).compareTo(other.isSetObjective_name());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetObjective_name()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.objective_name, other.objective_name);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetSatisfaction()).compareTo(other.isSetSatisfaction());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSatisfaction()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.satisfaction, other.satisfaction);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetWeight()).compareTo(other.isSetWeight());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetWeight()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.weight, other.weight);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("ObjectiveSatisfaction(");
    boolean first = true;

    sb.append("objective_name:");
    if (this.objective_name == null) {
      sb.append("null");
    } else {
      sb.append(this.objective_name);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("satisfaction:");
    sb.append(this.satisfaction);
    first = false;
    if (!first) sb.append(", ");
    sb.append("weight:");
    sb.append(this.weight);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class ObjectiveSatisfactionStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public ObjectiveSatisfactionStandardScheme getScheme() {
      return new ObjectiveSatisfactionStandardScheme();
    }
  }

  private static class ObjectiveSatisfactionStandardScheme extends org.apache.thrift.scheme.StandardScheme<ObjectiveSatisfaction> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ObjectiveSatisfaction struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // OBJECTIVE_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.objective_name = iprot.readString();
              struct.setObjective_nameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // SATISFACTION
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.satisfaction = iprot.readDouble();
              struct.setSatisfactionIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // WEIGHT
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.weight = iprot.readDouble();
              struct.setWeightIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, ObjectiveSatisfaction struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.objective_name != null) {
        oprot.writeFieldBegin(OBJECTIVE_NAME_FIELD_DESC);
        oprot.writeString(struct.objective_name);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(SATISFACTION_FIELD_DESC);
      oprot.writeDouble(struct.satisfaction);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(WEIGHT_FIELD_DESC);
      oprot.writeDouble(struct.weight);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ObjectiveSatisfactionTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public ObjectiveSatisfactionTupleScheme getScheme() {
      return new ObjectiveSatisfactionTupleScheme();
    }
  }

  private static class ObjectiveSatisfactionTupleScheme extends org.apache.thrift.scheme.TupleScheme<ObjectiveSatisfaction> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ObjectiveSatisfaction struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetObjective_name()) {
        optionals.set(0);
      }
      if (struct.isSetSatisfaction()) {
        optionals.set(1);
      }
      if (struct.isSetWeight()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetObjective_name()) {
        oprot.writeString(struct.objective_name);
      }
      if (struct.isSetSatisfaction()) {
        oprot.writeDouble(struct.satisfaction);
      }
      if (struct.isSetWeight()) {
        oprot.writeDouble(struct.weight);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ObjectiveSatisfaction struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.objective_name = iprot.readString();
        struct.setObjective_nameIsSet(true);
      }
      if (incoming.get(1)) {
        struct.satisfaction = iprot.readDouble();
        struct.setSatisfactionIsSet(true);
      }
      if (incoming.get(2)) {
        struct.weight = iprot.readDouble();
        struct.setWeightIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

