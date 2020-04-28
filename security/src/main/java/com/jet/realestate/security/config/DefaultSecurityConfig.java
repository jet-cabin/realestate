package com.jet.realestate.security.config;


import com.jet.realestate.common.config.GeneralConfiguration;
import com.jet.realestate.security.bo.AppUserDetails;
import com.jet.realestate.security.component.JwtAuthenticationTokenFilter;
import com.jet.realestate.security.component.RestAuthenticationEntryPoint;
import com.jet.realestate.security.component.RestfulAccessDeniedHandler;
import com.jet.realestate.security.model.Permission;
import com.jet.realestate.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;


/**
 * SpringSecurity的配置
 */
@Configuration("defaultSecurityConfig")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
//@EnableAutoConfiguration //导致重复导入AutoConfigurationImportSelector,
//@Import(GeneralConfiguration.class)
@ConditionalOnMissingBean(DefaultSecurityConfig.class)
@ComponentScan(basePackages = "com.jet.realestate.security")
//@ConditionalOnMissingBean(org.springframework.security.access.SecurityConfig.class)
public class DefaultSecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private UserService userService;

    public DefaultSecurityConfig(){

    }

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf()// 由于使用的是JWT，我们这里不需要csrf
                .disable()
                .sessionManagement()// 基于token，所以不需要session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, // 允许对于网站静态资源的无授权访问
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/swagger-resources/**",
                        "/v2/api-docs/**",
                        "/webjars/springfox-swagger-ui/**"
                )
                .permitAll()
                .antMatchers("/*/house/**","/**/actuator/**","/*/user/login", "/*/user/register","/*/permission/free/**","/*/user/free/**")// 对登录注册要允许匿名访问
                .permitAll()
                .antMatchers(HttpMethod.OPTIONS)//跨域请求会先进行一次options请求
                .permitAll()
//                .antMatchers("/**")//测试时全部运行访问
//                .permitAll()
                .anyRequest()// 除上面外的所有请求全部需要鉴权认证
                .authenticated();
        // 禁用缓存
        httpSecurity.headers().cacheControl();
        // 添加JWT filter
        httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        //添加自定义未授权和未登录结果返回
        httpSecurity.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return username -> {
//            User user = userService.getUserByName(username);
            User user=restTemplate.getForEntity("http://localhost:6060/v1/user/free?name="+username, User.class).getBody();
            if (user != null) {
                Permission[] permissionList =restTemplate.getForEntity("http://localhost:6060/v1/permission/free/permission/"+user.getId(), Permission[].class).getBody();//userService.getPermissionList(user.getId());
                return new AppUserDetails(user, Arrays.asList(permissionList));
            }
            throw new UsernameNotFoundException("用户名或密码错误");
        };
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

//    @Bean
//    public AccessDeniedHandler accessDeniedHandler(){
//        this.restfulAccessDeniedHandler= new RestfulAccessDeniedHandler();
//        return restfulAccessDeniedHandler;
//    }

}
