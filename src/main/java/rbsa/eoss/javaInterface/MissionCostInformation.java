/**
 * Autogenerated by Thrift Compiler (0.11.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package rbsa.eoss.javaInterface;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.11.0)", date = "2018-05-17")
public class MissionCostInformation implements org.apache.thrift.TBase<MissionCostInformation, MissionCostInformation._Fields>, java.io.Serializable, Cloneable, Comparable<MissionCostInformation> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("MissionCostInformation");

  private static final org.apache.thrift.protocol.TField ORBIT_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("orbit_name", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField LAUNCH_VEHICLE_FIELD_DESC = new org.apache.thrift.protocol.TField("launch_vehicle", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField MASS_BUDGET_FIELD_DESC = new org.apache.thrift.protocol.TField("mass_budget", org.apache.thrift.protocol.TType.MAP, (short)3);
  private static final org.apache.thrift.protocol.TField POWER_BUDGET_FIELD_DESC = new org.apache.thrift.protocol.TField("power_budget", org.apache.thrift.protocol.TType.MAP, (short)4);
  private static final org.apache.thrift.protocol.TField COST_BUDGET_FIELD_DESC = new org.apache.thrift.protocol.TField("cost_budget", org.apache.thrift.protocol.TType.MAP, (short)5);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new MissionCostInformationStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new MissionCostInformationTupleSchemeFactory();

  public String orbit_name; // required
  public String launch_vehicle; // required
  public java.util.Map<String,Double> mass_budget; // required
  public java.util.Map<String,Double> power_budget; // required
  public java.util.Map<String,Double> cost_budget; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ORBIT_NAME((short)1, "orbit_name"),
    LAUNCH_VEHICLE((short)2, "launch_vehicle"),
    MASS_BUDGET((short)3, "mass_budget"),
    POWER_BUDGET((short)4, "power_budget"),
    COST_BUDGET((short)5, "cost_budget");

    private static final java.util.Map<String, _Fields> byName = new java.util.HashMap<String, _Fields>();

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
        case 1: // ORBIT_NAME
          return ORBIT_NAME;
        case 2: // LAUNCH_VEHICLE
          return LAUNCH_VEHICLE;
        case 3: // MASS_BUDGET
          return MASS_BUDGET;
        case 4: // POWER_BUDGET
          return POWER_BUDGET;
        case 5: // COST_BUDGET
          return COST_BUDGET;
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
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ORBIT_NAME, new org.apache.thrift.meta_data.FieldMetaData("orbit_name", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.LAUNCH_VEHICLE, new org.apache.thrift.meta_data.FieldMetaData("launch_vehicle", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.MASS_BUDGET, new org.apache.thrift.meta_data.FieldMetaData("mass_budget", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING), 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE))));
    tmpMap.put(_Fields.POWER_BUDGET, new org.apache.thrift.meta_data.FieldMetaData("power_budget", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING), 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE))));
    tmpMap.put(_Fields.COST_BUDGET, new org.apache.thrift.meta_data.FieldMetaData("cost_budget", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING), 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE))));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(MissionCostInformation.class, metaDataMap);
  }

  public MissionCostInformation() {
  }

  public MissionCostInformation(
    String orbit_name,
    String launch_vehicle,
    java.util.Map<String,Double> mass_budget,
    java.util.Map<String,Double> power_budget,
    java.util.Map<String,Double> cost_budget)
  {
    this();
    this.orbit_name = orbit_name;
    this.launch_vehicle = launch_vehicle;
    this.mass_budget = mass_budget;
    this.power_budget = power_budget;
    this.cost_budget = cost_budget;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public MissionCostInformation(MissionCostInformation other) {
    if (other.isSetOrbit_name()) {
      this.orbit_name = other.orbit_name;
    }
    if (other.isSetLaunch_vehicle()) {
      this.launch_vehicle = other.launch_vehicle;
    }
    if (other.isSetMass_budget()) {
      java.util.Map<String,Double> __this__mass_budget = new java.util.HashMap<String,Double>(other.mass_budget);
      this.mass_budget = __this__mass_budget;
    }
    if (other.isSetPower_budget()) {
      java.util.Map<String,Double> __this__power_budget = new java.util.HashMap<String,Double>(other.power_budget);
      this.power_budget = __this__power_budget;
    }
    if (other.isSetCost_budget()) {
      java.util.Map<String,Double> __this__cost_budget = new java.util.HashMap<String,Double>(other.cost_budget);
      this.cost_budget = __this__cost_budget;
    }
  }

  public MissionCostInformation deepCopy() {
    return new MissionCostInformation(this);
  }

  @Override
  public void clear() {
    this.orbit_name = null;
    this.launch_vehicle = null;
    this.mass_budget = null;
    this.power_budget = null;
    this.cost_budget = null;
  }

  public String getOrbit_name() {
    return this.orbit_name;
  }

  public MissionCostInformation setOrbit_name(String orbit_name) {
    this.orbit_name = orbit_name;
    return this;
  }

  public void unsetOrbit_name() {
    this.orbit_name = null;
  }

  /** Returns true if field orbit_name is set (has been assigned a value) and false otherwise */
  public boolean isSetOrbit_name() {
    return this.orbit_name != null;
  }

  public void setOrbit_nameIsSet(boolean value) {
    if (!value) {
      this.orbit_name = null;
    }
  }

  public String getLaunch_vehicle() {
    return this.launch_vehicle;
  }

  public MissionCostInformation setLaunch_vehicle(String launch_vehicle) {
    this.launch_vehicle = launch_vehicle;
    return this;
  }

  public void unsetLaunch_vehicle() {
    this.launch_vehicle = null;
  }

  /** Returns true if field launch_vehicle is set (has been assigned a value) and false otherwise */
  public boolean isSetLaunch_vehicle() {
    return this.launch_vehicle != null;
  }

  public void setLaunch_vehicleIsSet(boolean value) {
    if (!value) {
      this.launch_vehicle = null;
    }
  }

  public int getMass_budgetSize() {
    return (this.mass_budget == null) ? 0 : this.mass_budget.size();
  }

  public void putToMass_budget(String key, double val) {
    if (this.mass_budget == null) {
      this.mass_budget = new java.util.HashMap<String,Double>();
    }
    this.mass_budget.put(key, val);
  }

  public java.util.Map<String,Double> getMass_budget() {
    return this.mass_budget;
  }

  public MissionCostInformation setMass_budget(java.util.Map<String,Double> mass_budget) {
    this.mass_budget = mass_budget;
    return this;
  }

  public void unsetMass_budget() {
    this.mass_budget = null;
  }

  /** Returns true if field mass_budget is set (has been assigned a value) and false otherwise */
  public boolean isSetMass_budget() {
    return this.mass_budget != null;
  }

  public void setMass_budgetIsSet(boolean value) {
    if (!value) {
      this.mass_budget = null;
    }
  }

  public int getPower_budgetSize() {
    return (this.power_budget == null) ? 0 : this.power_budget.size();
  }

  public void putToPower_budget(String key, double val) {
    if (this.power_budget == null) {
      this.power_budget = new java.util.HashMap<String,Double>();
    }
    this.power_budget.put(key, val);
  }

  public java.util.Map<String,Double> getPower_budget() {
    return this.power_budget;
  }

  public MissionCostInformation setPower_budget(java.util.Map<String,Double> power_budget) {
    this.power_budget = power_budget;
    return this;
  }

  public void unsetPower_budget() {
    this.power_budget = null;
  }

  /** Returns true if field power_budget is set (has been assigned a value) and false otherwise */
  public boolean isSetPower_budget() {
    return this.power_budget != null;
  }

  public void setPower_budgetIsSet(boolean value) {
    if (!value) {
      this.power_budget = null;
    }
  }

  public int getCost_budgetSize() {
    return (this.cost_budget == null) ? 0 : this.cost_budget.size();
  }

  public void putToCost_budget(String key, double val) {
    if (this.cost_budget == null) {
      this.cost_budget = new java.util.HashMap<String,Double>();
    }
    this.cost_budget.put(key, val);
  }

  public java.util.Map<String,Double> getCost_budget() {
    return this.cost_budget;
  }

  public MissionCostInformation setCost_budget(java.util.Map<String,Double> cost_budget) {
    this.cost_budget = cost_budget;
    return this;
  }

  public void unsetCost_budget() {
    this.cost_budget = null;
  }

  /** Returns true if field cost_budget is set (has been assigned a value) and false otherwise */
  public boolean isSetCost_budget() {
    return this.cost_budget != null;
  }

  public void setCost_budgetIsSet(boolean value) {
    if (!value) {
      this.cost_budget = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ORBIT_NAME:
      if (value == null) {
        unsetOrbit_name();
      } else {
        setOrbit_name((String)value);
      }
      break;

    case LAUNCH_VEHICLE:
      if (value == null) {
        unsetLaunch_vehicle();
      } else {
        setLaunch_vehicle((String)value);
      }
      break;

    case MASS_BUDGET:
      if (value == null) {
        unsetMass_budget();
      } else {
        setMass_budget((java.util.Map<String,Double>)value);
      }
      break;

    case POWER_BUDGET:
      if (value == null) {
        unsetPower_budget();
      } else {
        setPower_budget((java.util.Map<String,Double>)value);
      }
      break;

    case COST_BUDGET:
      if (value == null) {
        unsetCost_budget();
      } else {
        setCost_budget((java.util.Map<String,Double>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ORBIT_NAME:
      return getOrbit_name();

    case LAUNCH_VEHICLE:
      return getLaunch_vehicle();

    case MASS_BUDGET:
      return getMass_budget();

    case POWER_BUDGET:
      return getPower_budget();

    case COST_BUDGET:
      return getCost_budget();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ORBIT_NAME:
      return isSetOrbit_name();
    case LAUNCH_VEHICLE:
      return isSetLaunch_vehicle();
    case MASS_BUDGET:
      return isSetMass_budget();
    case POWER_BUDGET:
      return isSetPower_budget();
    case COST_BUDGET:
      return isSetCost_budget();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof MissionCostInformation)
      return this.equals((MissionCostInformation)that);
    return false;
  }

  public boolean equals(MissionCostInformation that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_orbit_name = true && this.isSetOrbit_name();
    boolean that_present_orbit_name = true && that.isSetOrbit_name();
    if (this_present_orbit_name || that_present_orbit_name) {
      if (!(this_present_orbit_name && that_present_orbit_name))
        return false;
      if (!this.orbit_name.equals(that.orbit_name))
        return false;
    }

    boolean this_present_launch_vehicle = true && this.isSetLaunch_vehicle();
    boolean that_present_launch_vehicle = true && that.isSetLaunch_vehicle();
    if (this_present_launch_vehicle || that_present_launch_vehicle) {
      if (!(this_present_launch_vehicle && that_present_launch_vehicle))
        return false;
      if (!this.launch_vehicle.equals(that.launch_vehicle))
        return false;
    }

    boolean this_present_mass_budget = true && this.isSetMass_budget();
    boolean that_present_mass_budget = true && that.isSetMass_budget();
    if (this_present_mass_budget || that_present_mass_budget) {
      if (!(this_present_mass_budget && that_present_mass_budget))
        return false;
      if (!this.mass_budget.equals(that.mass_budget))
        return false;
    }

    boolean this_present_power_budget = true && this.isSetPower_budget();
    boolean that_present_power_budget = true && that.isSetPower_budget();
    if (this_present_power_budget || that_present_power_budget) {
      if (!(this_present_power_budget && that_present_power_budget))
        return false;
      if (!this.power_budget.equals(that.power_budget))
        return false;
    }

    boolean this_present_cost_budget = true && this.isSetCost_budget();
    boolean that_present_cost_budget = true && that.isSetCost_budget();
    if (this_present_cost_budget || that_present_cost_budget) {
      if (!(this_present_cost_budget && that_present_cost_budget))
        return false;
      if (!this.cost_budget.equals(that.cost_budget))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetOrbit_name()) ? 131071 : 524287);
    if (isSetOrbit_name())
      hashCode = hashCode * 8191 + orbit_name.hashCode();

    hashCode = hashCode * 8191 + ((isSetLaunch_vehicle()) ? 131071 : 524287);
    if (isSetLaunch_vehicle())
      hashCode = hashCode * 8191 + launch_vehicle.hashCode();

    hashCode = hashCode * 8191 + ((isSetMass_budget()) ? 131071 : 524287);
    if (isSetMass_budget())
      hashCode = hashCode * 8191 + mass_budget.hashCode();

    hashCode = hashCode * 8191 + ((isSetPower_budget()) ? 131071 : 524287);
    if (isSetPower_budget())
      hashCode = hashCode * 8191 + power_budget.hashCode();

    hashCode = hashCode * 8191 + ((isSetCost_budget()) ? 131071 : 524287);
    if (isSetCost_budget())
      hashCode = hashCode * 8191 + cost_budget.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(MissionCostInformation other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetOrbit_name()).compareTo(other.isSetOrbit_name());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOrbit_name()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.orbit_name, other.orbit_name);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetLaunch_vehicle()).compareTo(other.isSetLaunch_vehicle());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLaunch_vehicle()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.launch_vehicle, other.launch_vehicle);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetMass_budget()).compareTo(other.isSetMass_budget());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMass_budget()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.mass_budget, other.mass_budget);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPower_budget()).compareTo(other.isSetPower_budget());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPower_budget()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.power_budget, other.power_budget);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCost_budget()).compareTo(other.isSetCost_budget());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCost_budget()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.cost_budget, other.cost_budget);
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
  public String toString() {
    StringBuilder sb = new StringBuilder("MissionCostInformation(");
    boolean first = true;

    sb.append("orbit_name:");
    if (this.orbit_name == null) {
      sb.append("null");
    } else {
      sb.append(this.orbit_name);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("launch_vehicle:");
    if (this.launch_vehicle == null) {
      sb.append("null");
    } else {
      sb.append(this.launch_vehicle);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("mass_budget:");
    if (this.mass_budget == null) {
      sb.append("null");
    } else {
      sb.append(this.mass_budget);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("power_budget:");
    if (this.power_budget == null) {
      sb.append("null");
    } else {
      sb.append(this.power_budget);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("cost_budget:");
    if (this.cost_budget == null) {
      sb.append("null");
    } else {
      sb.append(this.cost_budget);
    }
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

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class MissionCostInformationStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public MissionCostInformationStandardScheme getScheme() {
      return new MissionCostInformationStandardScheme();
    }
  }

  private static class MissionCostInformationStandardScheme extends org.apache.thrift.scheme.StandardScheme<MissionCostInformation> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, MissionCostInformation struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ORBIT_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.orbit_name = iprot.readString();
              struct.setOrbit_nameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // LAUNCH_VEHICLE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.launch_vehicle = iprot.readString();
              struct.setLaunch_vehicleIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // MASS_BUDGET
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map24 = iprot.readMapBegin();
                struct.mass_budget = new java.util.HashMap<String,Double>(2*_map24.size);
                String _key25;
                double _val26;
                for (int _i27 = 0; _i27 < _map24.size; ++_i27)
                {
                  _key25 = iprot.readString();
                  _val26 = iprot.readDouble();
                  struct.mass_budget.put(_key25, _val26);
                }
                iprot.readMapEnd();
              }
              struct.setMass_budgetIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // POWER_BUDGET
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map28 = iprot.readMapBegin();
                struct.power_budget = new java.util.HashMap<String,Double>(2*_map28.size);
                String _key29;
                double _val30;
                for (int _i31 = 0; _i31 < _map28.size; ++_i31)
                {
                  _key29 = iprot.readString();
                  _val30 = iprot.readDouble();
                  struct.power_budget.put(_key29, _val30);
                }
                iprot.readMapEnd();
              }
              struct.setPower_budgetIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // COST_BUDGET
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map32 = iprot.readMapBegin();
                struct.cost_budget = new java.util.HashMap<String,Double>(2*_map32.size);
                String _key33;
                double _val34;
                for (int _i35 = 0; _i35 < _map32.size; ++_i35)
                {
                  _key33 = iprot.readString();
                  _val34 = iprot.readDouble();
                  struct.cost_budget.put(_key33, _val34);
                }
                iprot.readMapEnd();
              }
              struct.setCost_budgetIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, MissionCostInformation struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.orbit_name != null) {
        oprot.writeFieldBegin(ORBIT_NAME_FIELD_DESC);
        oprot.writeString(struct.orbit_name);
        oprot.writeFieldEnd();
      }
      if (struct.launch_vehicle != null) {
        oprot.writeFieldBegin(LAUNCH_VEHICLE_FIELD_DESC);
        oprot.writeString(struct.launch_vehicle);
        oprot.writeFieldEnd();
      }
      if (struct.mass_budget != null) {
        oprot.writeFieldBegin(MASS_BUDGET_FIELD_DESC);
        {
          oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.DOUBLE, struct.mass_budget.size()));
          for (java.util.Map.Entry<String, Double> _iter36 : struct.mass_budget.entrySet())
          {
            oprot.writeString(_iter36.getKey());
            oprot.writeDouble(_iter36.getValue());
          }
          oprot.writeMapEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.power_budget != null) {
        oprot.writeFieldBegin(POWER_BUDGET_FIELD_DESC);
        {
          oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.DOUBLE, struct.power_budget.size()));
          for (java.util.Map.Entry<String, Double> _iter37 : struct.power_budget.entrySet())
          {
            oprot.writeString(_iter37.getKey());
            oprot.writeDouble(_iter37.getValue());
          }
          oprot.writeMapEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.cost_budget != null) {
        oprot.writeFieldBegin(COST_BUDGET_FIELD_DESC);
        {
          oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.DOUBLE, struct.cost_budget.size()));
          for (java.util.Map.Entry<String, Double> _iter38 : struct.cost_budget.entrySet())
          {
            oprot.writeString(_iter38.getKey());
            oprot.writeDouble(_iter38.getValue());
          }
          oprot.writeMapEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class MissionCostInformationTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public MissionCostInformationTupleScheme getScheme() {
      return new MissionCostInformationTupleScheme();
    }
  }

  private static class MissionCostInformationTupleScheme extends org.apache.thrift.scheme.TupleScheme<MissionCostInformation> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, MissionCostInformation struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetOrbit_name()) {
        optionals.set(0);
      }
      if (struct.isSetLaunch_vehicle()) {
        optionals.set(1);
      }
      if (struct.isSetMass_budget()) {
        optionals.set(2);
      }
      if (struct.isSetPower_budget()) {
        optionals.set(3);
      }
      if (struct.isSetCost_budget()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetOrbit_name()) {
        oprot.writeString(struct.orbit_name);
      }
      if (struct.isSetLaunch_vehicle()) {
        oprot.writeString(struct.launch_vehicle);
      }
      if (struct.isSetMass_budget()) {
        {
          oprot.writeI32(struct.mass_budget.size());
          for (java.util.Map.Entry<String, Double> _iter39 : struct.mass_budget.entrySet())
          {
            oprot.writeString(_iter39.getKey());
            oprot.writeDouble(_iter39.getValue());
          }
        }
      }
      if (struct.isSetPower_budget()) {
        {
          oprot.writeI32(struct.power_budget.size());
          for (java.util.Map.Entry<String, Double> _iter40 : struct.power_budget.entrySet())
          {
            oprot.writeString(_iter40.getKey());
            oprot.writeDouble(_iter40.getValue());
          }
        }
      }
      if (struct.isSetCost_budget()) {
        {
          oprot.writeI32(struct.cost_budget.size());
          for (java.util.Map.Entry<String, Double> _iter41 : struct.cost_budget.entrySet())
          {
            oprot.writeString(_iter41.getKey());
            oprot.writeDouble(_iter41.getValue());
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, MissionCostInformation struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.orbit_name = iprot.readString();
        struct.setOrbit_nameIsSet(true);
      }
      if (incoming.get(1)) {
        struct.launch_vehicle = iprot.readString();
        struct.setLaunch_vehicleIsSet(true);
      }
      if (incoming.get(2)) {
        {
          org.apache.thrift.protocol.TMap _map42 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.DOUBLE, iprot.readI32());
          struct.mass_budget = new java.util.HashMap<String,Double>(2*_map42.size);
          String _key43;
          double _val44;
          for (int _i45 = 0; _i45 < _map42.size; ++_i45)
          {
            _key43 = iprot.readString();
            _val44 = iprot.readDouble();
            struct.mass_budget.put(_key43, _val44);
          }
        }
        struct.setMass_budgetIsSet(true);
      }
      if (incoming.get(3)) {
        {
          org.apache.thrift.protocol.TMap _map46 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.DOUBLE, iprot.readI32());
          struct.power_budget = new java.util.HashMap<String,Double>(2*_map46.size);
          String _key47;
          double _val48;
          for (int _i49 = 0; _i49 < _map46.size; ++_i49)
          {
            _key47 = iprot.readString();
            _val48 = iprot.readDouble();
            struct.power_budget.put(_key47, _val48);
          }
        }
        struct.setPower_budgetIsSet(true);
      }
      if (incoming.get(4)) {
        {
          org.apache.thrift.protocol.TMap _map50 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.DOUBLE, iprot.readI32());
          struct.cost_budget = new java.util.HashMap<String,Double>(2*_map50.size);
          String _key51;
          double _val52;
          for (int _i53 = 0; _i53 < _map50.size; ++_i53)
          {
            _key51 = iprot.readString();
            _val52 = iprot.readDouble();
            struct.cost_budget.put(_key51, _val52);
          }
        }
        struct.setCost_budgetIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

