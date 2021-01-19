PowerSource µÁ‘¥
    String getName();
    String getDeviceName();
    double getTimeRemainingEstimated();
    double getTimeRemainingInstant();
    double getPowerUsageRate();
    double getVoltage();
    double getAmperage();
    boolean isPowerOnLine();
    boolean isCharging();
    boolean isDischarging();
    CapacityUnits getCapacityUnits();
    int getCurrentCapacity();
    int getMaxCapacity();
    int getDesignCapacity();
    int getCycleCount();
    String getChemistry();
    LocalDate getManufactureDate();
    String getManufacturer();
    String getSerialNumber();
    double getTemperature();
    boolean updateAttributes();

CapacityUnits
	MWH
	MAH
	RELATIVE
