package com.paper.admin.cache;

import com.paper.admin.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class TagCache {
    public static List<TagDTO> get() {
        List<TagDTO> tagDTOS = new ArrayList<>();
        TagDTO program = new TagDTO();
        program.setCategoryName("动画");
        program.setTags(Arrays.asList("恋爱","校园","冒险","运动","治愈","职场","后宫","异世界","剧情"));
        tagDTOS.add(program);

        TagDTO framework = new TagDTO();
        framework.setCategoryName("漫画");
        framework.setTags(Arrays.asList("轻小说","校园","冒险","运动","治愈","职场","后宫","异世界","剧情"));
        tagDTOS.add(framework);


        TagDTO server = new TagDTO();
        server.setCategoryName("游戏");
        server.setTags(Arrays.asList("动作","冒险","模拟","角色扮演","休闲","其他"));
        tagDTOS.add(server);

        return tagDTOS;
    }

    //判断非法标签 已弃用
    public static String filterInvalid(String tags) {
        String[] split = StringUtils.split(tags, ",");
        List<TagDTO> tagDTOS = get();

        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t -> StringUtils.isBlank(t) || !tagList.contains(t)).collect(Collectors.joining(","));
        return invalid;
    }

    public static void main(String[] args) {
        int i = (5 - 1) >>> 1;
        System.out.println(i);
    }
}
