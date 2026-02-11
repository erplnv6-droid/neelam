package com.SCM.config;

import java.util.Arrays;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.SCM.CustomProvider.CustomAuthenticationProvider1;
import com.SCM.Security.DistributorDetailsService;
import com.SCM.Security.RetailerDetailsService;
import com.SCM.Security.SupplierDetailsService;
import com.SCM.jwt.AuthEntryPointJwt;
import com.SCM.jwt.AuthTokenFilter;
import com.SCM.serviceimpl.UserDetailsServiceImpl;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    DistributorDetailsService distributorService;

    @Autowired
    RetailerDetailsService retailerDetailsService;
    
    @Autowired
    SupplierDetailsService supplierDetailsService;
  
    
    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    
    @Bean
    public CustomAuthenticationProvider1 authenticationProvider1() {
        return new CustomAuthenticationProvider1();
    }
    
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }


    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
    	authenticationManagerBuilder.authenticationProvider(authenticationProvider1());
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        authenticationManagerBuilder.userDetailsService(distributorService).passwordEncoder(passwordEncoder());
        authenticationManagerBuilder.userDetailsService(retailerDetailsService).passwordEncoder(passwordEncoder());
        authenticationManagerBuilder.userDetailsService(supplierDetailsService).passwordEncoder(passwordEncoder());
    }
    
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    
    
    
    @Override
    public void setAuthenticationConfiguration(AuthenticationConfiguration authenticationConfiguration) {
        super.setAuthenticationConfiguration(authenticationConfiguration);
    }
    
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	System.out.println("From Configure:");
    	System.out.println(SecurityContextHolder.getContext().getAuthentication());
        http.cors().and().csrf().disable()
//    	http.csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/zone/getAll").permitAll()
                .antMatchers("/ws/**").permitAll()
                .antMatchers("/wsnative/**").permitAll()   // Native ke liye plain WebSocket

                .antMatchers("/ws/**", "/ws/**/**", "/topic/**", "/queue/**").permitAll()
                .antMatchers("/ws/**", "/topic/**", "/queue/**").permitAll()
                .antMatchers("/api/warehouseupload/page/**").permitAll()
                .antMatchers("/api/images/**").permitAll() // Added by ::VIVEKG
                .antMatchers("/scm/api/**").authenticated()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/signin").permitAll()
                /* .defaultSuccessUrl("/user")*/
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/signout"))
                .permitAll()
                .deleteCookies("JSESSIONID", "remember-me")
                .logoutSuccessUrl("/signin");

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
    
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
            .ignoring()
            .antMatchers("/ws/**")
            .antMatchers("/api/branch/**")
            .antMatchers("/api/warehouse/**")
//            .antMatchers("/api/supplier/**")
            .antMatchers("/api/purchase/**")
            .antMatchers("/api/supplier/**")
            .antMatchers("/api/sdn/page/**")            
                .antMatchers("/api/retailer/create")
                .antMatchers("/api/retailertemporary/create1")
                .antMatchers("/api/retailertemporary/create")
//                .antMatchers("/api/zone/getAll")
                .antMatchers("/api/retailer/create1")
                .antMatchers("/api/brand/getById/{id}")
                .antMatchers("/api/openingstock/getAll")
                .antMatchers("/api/product/getosp")
                .antMatchers("/api/product/**")
                .antMatchers("/api/workorder/addEstimatedDays/{id}/{pid}/{days}/{primaryItemId}")
                .antMatchers("/api/workorder/delete/{id}")
                .antMatchers("/api/brand/delete/{id}")
                .antMatchers("/api/warehouse/delete/{id}")
                .antMatchers("/api/staff/delete/{id}")
                .antMatchers("/api/auth/signin")
                .antMatchers("/api/primaryworkorder/delete/{id}")
                .antMatchers("/api/distributor/state/{stateId}")
                .antMatchers("/Images/**")
                .antMatchers("/BarcodeImages/**")
                .antMatchers("/ContractManagementDoc/**")
                .antMatchers("/api/salesAgent/**")               
                .antMatchers("/api/MinutesOfmeeting/**")
                .antMatchers("/api/profilepic/**")
                .antMatchers("/api/profilepic/upload")
                .antMatchers("/api/ContractManagementDoc/**")
                .antMatchers("/api/assets/**")
                .antMatchers("/api/invoiceAttachment/**")
                .antMatchers("/api/invoiceAttachmentPdf/**")
                .antMatchers("/api/auth/otp")
                .antMatchers("/api/salesAgent/**")  
                .antMatchers("/Launchingimag/**")
                .antMatchers("/Areaimg/**")
                .antMatchers("/invoiceAttachment/**")
                .antMatchers("/invoiceAttachmentPdf/**")
                .antMatchers("/ContractManagementDoc/**")
                .antMatchers("/MinutesOfmeeting/**")
                .antMatchers("/assets/**")
                .antMatchers("/invoiceupload/**")
                .antMatchers("/profilepic/**")
                .antMatchers("/api/launching/**")
                .antMatchers("/api/area/**")
                .antMatchers("/PublicKey/**")
                .antMatchers("/SalesAgent/**")
                .antMatchers("/api/warehouseupload/page/**")
                .antMatchers("/webapps/scm/WEB-INF/classes/static/Images/PublicKey/**");
    }

    
  @Bean
  CorsConfigurationSource corsConfigurationSource() {
      CorsConfiguration configuration = new CorsConfiguration();
      configuration.setAllowCredentials(true);
//      configuration.setAllowedOrigins(Arrays.asList("http://202.189.228.22:3000","http://192.168.1.211:3000","http://192.168.1.88:3000"));
//      configuration.setAllowedOrigins(Arrays.asList("http://216.10.245.135:3000","http://216.10.245.135:8080"));
      configuration.addAllowedOrigin("http://216.10.245.135:3000"); // Frontend domain
      configuration.addAllowedOrigin("http://216.10.245.135:8080"); // Backend 
      configuration.addAllowedOrigin("http://43.225.54.138:3000"); // Frontend domain
      configuration.addAllowedOrigin("http://43.225.54.138:8080"); // Backend 
      configuration.addAllowedHeader("*");
      configuration.addAllowedOrigin("http://localhost:3000");
      configuration.addAllowedOrigin("http://localhost:3001");
      configuration.addAllowedOrigin("http://216.10.245.135:3000");
      configuration.addAllowedOrigin("http://216.10.244.222:3000");
//      configuration.addAllowedOrigin("http://202.10.245.135:3000"); 
      configuration.addAllowedOrigin("http://192.168.1.105:3000"); 
      configuration.addAllowedOrigin("http://192.168.1.10:3000"); 
      configuration.addAllowedOrigin("http://192.168.1.10:3001");
      configuration.addAllowedOrigin("http://192.168.1.60:3000"); 
      configuration.addAllowedOrigin("http://192.168.1.60:3001");
      configuration.addAllowedOrigin("http://192.168.1.60:3002");
      configuration.addAllowedOrigin("http://192.168.1.61:3000"); 
      configuration.addAllowedOrigin("http://192.168.1.145:3000");
      configuration.addAllowedOrigin("http://192.168.1.145:3001");
      configuration.addAllowedOrigin("http://192.168.1.211:3001");
      configuration.addAllowedOrigin("http://192.168.1.211:3000");
      configuration.addAllowedOrigin("http://192.168.1.113:8080");
      configuration.addAllowedOrigin("http://192.168.1.88:3000"); 
      configuration.addAllowedOrigin("http://192.1.168.211:3000"); 
      configuration.addAllowedOrigin("http://192.1.168.211:3001"); 
      configuration.addAllowedOrigin("http://192.1.168.211:8080"); 
      configuration.addAllowedOrigin("http://202.189.228.22:3000");
      configuration.addAllowedOrigin("http://202.189.228.22:3001");
      configuration.addAllowedOrigin("http://202.189.228.22:8000");
      configuration.addAllowedOrigin("http://202.189.228.22:8081");
      configuration.addAllowedOrigin("http://192.168.1.34:3000");
      configuration.addAllowedOrigin("http://192.168.1.21:3000");
      configuration.addAllowedOrigin("http://192.168.1.25:3000");
      configuration.addAllowedOrigin("http://192.168.1.143:8081");
      configuration.addAllowedOrigin("http://192.168.1.143:3000");
      configuration.addAllowedOrigin("http://103.125.217.81:3000");
      configuration.addAllowedOrigin("http://119.18.62.102:3000");
      configuration.addAllowedOrigin("http://119.18.62.102:80");
      configuration.addAllowedOrigin("http://119.18.62.102");
      configuration.addAllowedOrigin("http://192.168.1.141:3000");  
      configuration.addAllowedOrigin("http://192.168.1.103:3000");
      configuration.addAllowedOrigin("http://192.168.1.105:3000");
      configuration.addAllowedOrigin("http://192.168.1.34:3000");
      configuration.addAllowedOrigin("http://192.168.1.123:3000");
      configuration.addAllowedOrigin("http://192.168.1.123:3001");
      configuration.addAllowedOrigin("http://192.168.1.61:3001");
      configuration.addAllowedOrigin("http://192.168.1.35:3000");
      configuration.addAllowedOrigin("http://192.168.1.35:8081");
      configuration.addAllowedOrigin("http://192.168.1.72:3000");
      configuration.addAllowedOrigin("http://192.168.1.143:3000");
      configuration.addAllowedOrigin("http://192.168.1.143:8081");
      configuration.addAllowedOrigin("http://192.168.1.12:8081");
      configuration.addAllowedOrigin("http://192.168.1.12:3000");
      configuration.addAllowedOrigin("http://192.168.1.7:3000");
      configuration.addAllowedOrigin("http://192.168.1.7:8080");
      configuration.addAllowedOrigin("http://192.168.1.71:3000");
      configuration.addAllowedOrigin("https://swiftbyneelam.com:3000");
      configuration.addAllowedOrigin("https://swiftbyneelam.com:8080");
      configuration.addAllowedOrigin("https://swiftbyneelam.com:80");
      configuration.addAllowedOrigin("https://swiftbyneelam.com");
      configuration.addAllowedOrigin("http://192.168.1.3:3000");
      configuration.addAllowedOrigin("http://192.168.1.3:3001");

      configuration.addAllowedOrigin("http://192.168.1.102:3000");
      configuration.addAllowedOrigin("http://192.168.1.102:3001");
      
      configuration.addAllowedOrigin("http://swiftbyneelam.com:3000");
      configuration.addAllowedOrigin("http://swiftbyneelam.com:8080");
      configuration.addAllowedOrigin("http://swiftbyneelam.com:80");
      configuration.addAllowedOrigin("http://swiftbyneelam.com");

      
      configuration.addAllowedOrigin("http://216.10.244.222:3000");
      configuration.addAllowedOrigin("http://216.10.244.222:8080");
      configuration.addAllowedOrigin("http://216.10.244.222:80");
      configuration.addAllowedOrigin("http://216.10.244.222");
      
      
      configuration.addAllowedOrigin("http://216.10.244.159:3000");
      configuration.addAllowedOrigin("http://216.10.244.159:8080");
      configuration.addAllowedOrigin("http://216.10.244.159:80");
      configuration.addAllowedOrigin("http://216.10.244.159");
      
      configuration.addAllowedOrigin("http://43.225.54.138");
      configuration.addAllowedOrigin("http://43.225.54.138:8080");
      configuration.addAllowedOrigin("http://43.225.54.138:80");
      configuration.addAllowedOrigin("http://43.225.54.138:3000");
      
      
      configuration.addAllowedOrigin("http://119.18.48.67:3000");
      configuration.addAllowedOrigin("http://119.18.48.67:80");
      configuration.addAllowedOrigin("https://loopfit.in:3000");
      configuration.addAllowedOrigin("https://loopfit.in:80");
      configuration.addAllowedOrigin("https://loopfit.in");
      
      configuration.addAllowedOrigin("https://192.168.1.214:3000");
      configuration.addAllowedOrigin("https://192.168.1.214:80");
      
      configuration.addAllowedOrigin("http://192.168.1.139:3000");
      configuration.addAllowedOrigin("http://192.168.1.47:3000");
      configuration.addAllowedOrigin("http://192.168.1.47:8080");
      configuration.addAllowedOrigin("http://192.168.1.47:3001");
      configuration.addAllowedOrigin("http://192.168.1.79:3000");
      configuration.addAllowedOrigin("http://192.168.1.79:8080");
      configuration.addAllowedOrigin("http://192.168.1.79:3001");
      configuration.addAllowedOrigin("http://192.168.1.72:3000");
      configuration.addAllowedOrigin("http://192.168.1.72:8080");
      configuration.addAllowedOrigin("http://192.168.1.72:3001");
      configuration.addAllowedOrigin("http://192.168.1.78:3000");
      configuration.addAllowedOrigin("http://192.168.1.78:8080");
      configuration.addAllowedOrigin("http://192.168.1.78:3001");
      configuration.addAllowedOrigin("http://192.168.1.46:3001");
      configuration.addAllowedOrigin("http://192.168.1.46:3000");
      
      
      configuration.addAllowedOrigin("http://192.168.1.213:3000");
      configuration.addAllowedOrigin("http://192.168.1.231:3000");
      configuration.addAllowedOrigin("http://192.168.1.213:3001");
      configuration.addAllowedOrigin("http://192.168.1.76:3000");
      configuration.addAllowedOrigin("http://192.168.1.76:3001");
      configuration.addAllowedOrigin("http://192.168.1.224:3000");
      configuration.addAllowedOrigin("http://192.168.1.224:3001");
      configuration.addAllowedOrigin("http://192.168.1.214:3000");
      configuration.addAllowedOrigin("http://119.18.48.67:3000");
      configuration.addAllowedOrigin("http://119.18.48.67");
      configuration.addAllowedOrigin("https://119.18.48.67:3000");
      configuration.addAllowedOrigin("https://119.18.48.67");
      configuration.addAllowedOrigin("http://swiftbyneelam.com");
      configuration.addAllowedOrigin("https://swiftbyneelam.com");
      configuration.addAllowedOrigin("http://www.swiftbyneelam.com");
      configuration.addAllowedOrigin("https://www.swiftbyneelam.com");
      configuration.addAllowedOrigin("http://192.168.1.9:3000");
      configuration.addAllowedOrigin("http://192.168.1.9:3001");
      
      configuration.addAllowedOrigin("http://192.168.1.72:3000");
      configuration.addAllowedOrigin("http://192.168.1.72:3001");
      
      configuration.setAllowedMethods(Arrays.asList("*"));
      configuration.addAllowedMethod("*");
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/**", configuration); 
      return source;
  }

}