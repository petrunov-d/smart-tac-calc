package com.dp.trains.services;

import com.dp.trains.model.dto.PasswordChangeDto;
import com.dp.trains.model.dto.UserDto;
import com.dp.trains.model.entities.user.AuthorityEntity;
import com.dp.trains.model.entities.user.UserAccessEntitiy;
import com.dp.trains.model.entities.user.UserEntity;
import com.dp.trains.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@ToString
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class TrainsUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Qualifier("bcryptPasswordEncoder")
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDetails userDetails = userRepository.findByUsername(username);

        log.info("Found user details for username: " + username + " -> " + userDetails.toString());

        return userDetails;
    }

    @Transactional
    public void deleteByUserName(String username) {

        UserDetails userDetails = this.userRepository.findByUsername(username);

        if (userDetails == null) {

            throw new IllegalStateException("User with username: " + username + " was not found");
        }

        log.info("Delete by username:" + username + " -> " + userDetails.toString());

        userRepository.deleteByUsername(username);
    }

    @Transactional(readOnly = true)
    public List<UserEntity> getAll() {

        return userRepository.findAll();
    }

    @Transactional
    public UserEntity add(UserDto userDto) {

        UserEntity userEntity = new UserEntity();
        userEntity.setEnabled(true);
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Set<UserAccessEntitiy> accessesSet = userDto.getUserAccesses()
                .stream()
                .map(x -> UserAccessEntitiy
                        .builder()
                        .username(userEntity)
                        .userAccess(x.name())
                        .build())
                .collect(Collectors.toSet());

        Set<AuthorityEntity> authorityEntitySet = userDto.getAuthorities()
                .stream()
                .map(x -> AuthorityEntity
                        .builder()
                        .username(userEntity)
                        .authority(x.getName())
                        .build())
                .collect(Collectors.toSet());

        userEntity.setUserAccesses(accessesSet);
        userEntity.setAuthorities(authorityEntitySet);


        log.info(this.getClass().getSimpleName() + " add: " + userEntity.toString());

        return userRepository.save(userEntity);
    }

    @Transactional
    public UserEntity update(UserEntity entityToUpdate) {

        UserEntity userEntityFromDb = userRepository.findByUsername(entityToUpdate.getUsername());

        if (userEntityFromDb == null) {

            throw new IllegalStateException("User not found");
        }

        log.info("About to update item " + userEntityFromDb.toString() + " to " + entityToUpdate.toString());

        userEntityFromDb.setUsername(entityToUpdate.getUsername());
        userEntityFromDb.setPassword(entityToUpdate.getPassword());
        userEntityFromDb.setEnabled(entityToUpdate.getEnabled());
        userEntityFromDb.setAuthorities((Set<AuthorityEntity>) entityToUpdate.getAuthorities());

        return userRepository.save(userEntityFromDb);
    }

    @Transactional
    public UserEntity update(UserEntity userEntity, UserDto userDto) {

        UserEntity userEntityFromDb = userRepository.findByUsername(userDto.getUsername());

        if (userEntityFromDb == null) {

            throw new IllegalStateException("User not found");
        }

        userEntityFromDb.setUsername(userDto.getUsername());
        userEntityFromDb.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Set<UserAccessEntitiy> accessesSet = userDto.getUserAccesses()
                .stream()
                .map(x -> UserAccessEntitiy
                        .builder()
                        .username(userEntity)
                        .userAccess(x.name())
                        .build())
                .collect(Collectors.toSet());

        Set<AuthorityEntity> authorityEntitySet = userDto.getAuthorities()
                .stream()
                .map(x -> AuthorityEntity
                        .builder()
                        .username(userEntityFromDb)
                        .authority(x.getName())
                        .build())
                .collect(Collectors.toSet());

        userEntityFromDb.setAuthorities(authorityEntitySet);
        userEntityFromDb.setUserAccesses(accessesSet);

        log.info("About to update item " + userEntityFromDb.toString() + " to " + userEntityFromDb.toString());

        return userRepository.save(userEntityFromDb);
    }

    @Transactional
    public Boolean changePassword(PasswordChangeDto passwordChangeDto) {

        UserEntity userEntity = userRepository.findByUsername(passwordChangeDto.getUsername());

        if (userEntity == null) {

            throw new IllegalStateException("User not found for username:" + passwordChangeDto.getUsername());
        }

        if (!passwordChangeDto.getNewPassword().equals(passwordChangeDto.getNewPasswordConfirm())) {
            throw new IllegalStateException("Passwords must match");
        }

        if (!userEntity.getPassword().equals(passwordEncoder.encode(passwordChangeDto.getOldPassword()))) {

            throw new IllegalStateException("Old password doesn't match");
        }

        userEntity.setPassword(passwordEncoder.encode(passwordChangeDto.getNewPassword()));

        userRepository.save(userEntity);

        return true;
    }

    @Transactional(readOnly = true)
    public Collection<UserEntity> fetch(int offset, int limit) {

        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public int count() {
        return (int) userRepository.count();
    }
}
