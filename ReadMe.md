```agsl

    @Getter
    @Entity
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Table(name = "user")
    public class User {
    
        @Id
        private Long id;
    
        @Column(name = "name", nullable = false, length = 100)
        private String name;
    
        @Column(name = "username", nullable = false)
        private String username;
    
        @Embedded
        @AttributeOverride(name = "street", column = @Column(name = "street"))
        @AttributeOverride(name = "city", column = @Column(name = "city"))
        @AttributeOverride(name = "zipcode", column = @Column(name = "zipcode"))
        @AttributeOverride(name = "geo.lat", column = @Column(name = "geo_lat"))
        @AttributeOverride(name = "geo.lng", column = @Column(name = "geo_lng"))
        private Address address;
    
        @Column(name = "phone", nullable = false)
        private String phone;
    
        @Column(name = "website", nullable = false)
        private String website;
    
        @Embedded
        @AttributeOverride(name = "name", column = @Column(name = "company_name"))
        @AttributeOverride(name = "catchPhrase", column = @Column(name = "company_catchPhrase"))
        @AttributeOverride(name = "bs", column = @Column(name = "company_bs"))
        private Company company;
    
    }
    
    @Builder
    public record UserDto(Long id, String name, String username, AddressDto addressDto, String phone, String website, CompanyDto companyDto) {
    
        public record AddressDto(String street, String city, String zipcode, GeoDto geoDto) {}
        public record GeoDto(String lat, String lng) {}
        public record CompanyDto(String name, String catchPhrase, String bs) {}
    
    }
    
    @Mapper(componentModel = "spring")
    public interface UserMapper {

        @Mapping(target = "address", source = "addressDto")
        @Mapping(target = "company", source = "companyDto")
        User mapToEntity(UserDto userDto);
    
        @InheritInverseConfiguration
        UserDto mapToDto(User user);
    }

```
