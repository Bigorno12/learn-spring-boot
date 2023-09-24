package mu.tutorial.learnspringboot.mapper;

import javax.annotation.processing.Generated;
import mu.tutorial.learnspringboot.dto.UserDto;
import mu.tutorial.learnspringboot.emmbeded.Address;
import mu.tutorial.learnspringboot.emmbeded.Company;
import mu.tutorial.learnspringboot.entity.User;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-24T20:38:29+0400",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.6 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User mapToEntity(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.address( addressDtoToAddress( userDto.addressDto() ) );
        user.company( companyDtoToCompany( userDto.companyDto() ) );
        user.id( userDto.id() );
        user.name( userDto.name() );
        user.username( userDto.username() );
        user.phone( userDto.phone() );
        user.website( userDto.website() );

        return user.build();
    }

    @Override
    public UserDto mapToDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto.UserDtoBuilder userDto = UserDto.builder();

        userDto.addressDto( addressToAddressDto( user.getAddress() ) );
        userDto.companyDto( companyToCompanyDto( user.getCompany() ) );
        userDto.id( user.getId() );
        userDto.name( user.getName() );
        userDto.username( user.getUsername() );
        userDto.phone( user.getPhone() );
        userDto.website( user.getWebsite() );

        return userDto.build();
    }

    protected Address addressDtoToAddress(UserDto.AddressDto addressDto) {
        if ( addressDto == null ) {
            return null;
        }

        Address.AddressBuilder address = Address.builder();

        address.street( addressDto.street() );
        address.city( addressDto.city() );
        address.zipcode( addressDto.zipcode() );

        return address.build();
    }

    protected Company companyDtoToCompany(UserDto.CompanyDto companyDto) {
        if ( companyDto == null ) {
            return null;
        }

        Company.CompanyBuilder company = Company.builder();

        company.name( companyDto.name() );
        company.catchPhrase( companyDto.catchPhrase() );
        company.bs( companyDto.bs() );

        return company.build();
    }

    protected UserDto.AddressDto addressToAddressDto(Address address) {
        if ( address == null ) {
            return null;
        }

        String street = null;
        String city = null;
        String zipcode = null;

        street = address.getStreet();
        city = address.getCity();
        zipcode = address.getZipcode();

        UserDto.GeoDto geoDto = null;

        UserDto.AddressDto addressDto = new UserDto.AddressDto( street, city, zipcode, geoDto );

        return addressDto;
    }

    protected UserDto.CompanyDto companyToCompanyDto(Company company) {
        if ( company == null ) {
            return null;
        }

        String name = null;
        String catchPhrase = null;
        String bs = null;

        name = company.getName();
        catchPhrase = company.getCatchPhrase();
        bs = company.getBs();

        UserDto.CompanyDto companyDto = new UserDto.CompanyDto( name, catchPhrase, bs );

        return companyDto;
    }
}
