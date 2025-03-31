close all; clear; clc;
x = 0:0.01:1;
r = 1;
y = sqrt(r*r-x.*x);

N = 100;
figure('Name','Wizualizacja metody Monte Carlo');

for i = 1:4
    rand_x = rand(1, N);
    rand_y = rand(1, N);
    inside_circle = (rand_x.^2 + rand_y.^2) <= r^2;
    suma = sum(inside_circle);
    Pi = 4*suma/N;

    subplot(2,2,i), plot(x, y, 'black');
    hold on;
    plot(rand_x(inside_circle), rand_y(inside_circle), 'b.');
    plot(rand_x(~inside_circle), rand_y(~inside_circle), 'r.');
    axis equal;
    xlim([0, 1]);
    ylim([0, 1]);
    title(['Losowanie punktów N = ', num2str(N)]);
    xlabel('x');
    ylabel('y');
    text(0.05,0.1, sprintf('PI = %.5f', Pi), 'BackgroundColor', 'w')
    hold off;
    N = N*10;
end

%%
N = 0:100:100000;
Pi_vec = zeros(size(N));
figure('Name','Wykaz zbliżania się do wartości pi wraz z wzrostem punktów');

hold on;
for i = 1:3
    for n = 1:length(N)
        rand_x = rand(1, N(n));
        rand_y = rand(1, N(n));
    
        inside_circle = (rand_x.^2 + rand_y.^2) <= r^2;
        suma = sum(inside_circle);
        Pi_vec(n) = 4*suma/N(n);
    end
    plot(N, Pi_vec)
end

plot(N, pi*ones(size(N)), 'k', LineWidth= 2)
hold off;
title('Wizulaizacja 3 rożnych serii dla zwiekszającej sie liczby losowań');
xlabel('liczna lodowań n');
ylabel('Estymacja pi');
%ylim([3.0, 3.3]);

%%

Pi_box = zeros(10,4);
figure('Name','Wizulalizacja boxplotem');
N = 10;

for i = 1:5
    for j = 1:10
        rand_x = rand(1, N);
        rand_y = rand(1, N);

        inside_circle = (rand_x.^2 + rand_y.^2) <= r^2;
        suma = sum(inside_circle);
        Pi_box(j, i) = 4*suma/N;
    end
    N = N*10;
end

boxplot(Pi_box)
title('Rozkład estymacji liczby pi dla różnych liczby punktów');
xlabel('Liczba punktów: 10^N');
ylabel('Estymacja pi');