package com.gym.bstrong.service;

import com.gym.bstrong.domain.Member;
import com.gym.bstrong.domain.Subscription;
import com.gym.bstrong.dto.SubscriptionInDto;
import com.gym.bstrong.dto.SubscriptionOutDto;
import com.gym.bstrong.exception.MemberNotFoundException;
import com.gym.bstrong.exception.SubscriptionNotFoundException;
import com.gym.bstrong.repository.MemberRepository;
import com.gym.bstrong.repository.SubscriptionRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<SubscriptionOutDto> findAll(String type, Long memberId, Boolean active) {
        List<Subscription> subscriptions;
        if (type == null && memberId == null && active == null) {
            subscriptions = subscriptionRepository.findAll();
        } else {
            subscriptions = subscriptionRepository.findByFilters(type, memberId, active);
        }
        return modelMapper.map(subscriptions, new TypeToken<List<SubscriptionOutDto>>() {}.getType());
    }

    public SubscriptionOutDto findById(long id) throws SubscriptionNotFoundException {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(SubscriptionNotFoundException::new);
        return modelMapper.map(subscription, SubscriptionOutDto.class);
    }

    public SubscriptionOutDto addSubscription(SubscriptionInDto subscriptionInDto) throws MemberNotFoundException {
        Subscription subscription = modelMapper.map(subscriptionInDto, Subscription.class);

        Member member = memberRepository.findById(subscriptionInDto.getMemberId())
                .orElseThrow(MemberNotFoundException::new);
        subscription.setMember(member);

        Subscription savedSubscription = subscriptionRepository.save(subscription);
        return modelMapper.map(savedSubscription, SubscriptionOutDto.class);
    }

    public SubscriptionOutDto modifySubscription(long id, SubscriptionInDto subscriptionInDto)
            throws SubscriptionNotFoundException, MemberNotFoundException {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(SubscriptionNotFoundException::new);

        modelMapper.map(subscriptionInDto, subscription);
        subscription.setId(id);

        if (subscription.getMember().getId() != subscriptionInDto.getMemberId()) {
            Member member = memberRepository.findById(subscriptionInDto.getMemberId())
                    .orElseThrow(MemberNotFoundException::new);
            subscription.setMember(member);
        }

        Subscription modifiedSubscription = subscriptionRepository.save(subscription);
        return modelMapper.map(modifiedSubscription, SubscriptionOutDto.class);
    }

    public void deleteSubscription(long id) throws SubscriptionNotFoundException {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(SubscriptionNotFoundException::new);
        subscriptionRepository.delete(subscription);
    }
}
