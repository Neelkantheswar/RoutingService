package org.entity;

public class Address {
    private String streetNumber;
    private String city;
    private String state;
    private String country;
    private Location location;

    private Address(Builder builder) {
        streetNumber = builder.streetNumber;
        city = builder.city;
        state = builder.state;
        country = builder.country;
        location = builder.location;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "Address{" +
                "streetNumber='" + streetNumber + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", location=" + location +
                '}';
    }

    public static final class Builder {
        private String streetNumber;
        private String city;
        private String state;
        private String country;
        private Location location;

        private Builder() {
        }

        public Builder streetNumber(String val) {
            streetNumber = val;
            return this;
        }

        public Builder city(String val) {
            city = val;
            return this;
        }

        public Builder state(String val) {
            state = val;
            return this;
        }

        public Builder country(String val) {
            country = val;
            return this;
        }

        public Builder location(Location val) {
            location = val;
            return this;
        }

        public Address build() {
            return new Address(this);
        }
    }
}
